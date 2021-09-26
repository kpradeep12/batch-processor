package net.thetechstack.batchprocessor.flatfilejob;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.batch.item.database.ItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.validation.BindException;

@Configuration
public class FlatFileJobConfiguration {
    @Autowired
    public JobBuilderFactory jobBuilderFactory;
    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    private static final Logger log = LoggerFactory.getLogger(FlatFileJobConfiguration.class);

    @Bean
    @StepScope
    public FlatFileItemReader<Map<String, Object>> reader(@Value("#{jobParameters['input-file']}") String inputFile,
            @Value("#{jobParameters['delimiter']}") String delimiter,
            @Value("#{jobParameters['column-names']}") String columnNames) {

        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer(delimiter);
        tokenizer.setNames(columnNames.split(","));

        DefaultLineMapper<Map<String, Object>> mapper = new DefaultLineMapper<>();
        mapper.setLineTokenizer(tokenizer);
        mapper.setFieldSetMapper(new FieldSetMapper<Map<String, Object>>() {
            @Override
            public Map<String, Object> mapFieldSet(FieldSet fieldSet) throws BindException {
                return IntStream.range(0, fieldSet.getFieldCount()).boxed().collect(
                        Collectors.toMap(index -> fieldSet.getNames()[index], index -> fieldSet.getValues()[index]));

            }
        });

        FlatFileItemReader<Map<String, Object>> itemReader = new FlatFileItemReader<>();
        itemReader.setLinesToSkip(0);
        itemReader.setName("flat-file-reader");
        itemReader.setResource(new FileSystemResource(inputFile));
        itemReader.setLineMapper(mapper);
        return itemReader;
    }

    @Bean
    @StepScope
    public JdbcBatchItemWriter<Map<String, Object>> writer(DataSource dataSource,
            @Value("#{jobParameters['table-name']}") String tableName,
            @Value("#{jobParameters['column-names']}") String columnNames) {

        String columnValues = Stream.of(columnNames.split(","))
        .map(columnName -> ":".concat(columnName))
        .collect(Collectors.joining(","));

        return new JdbcBatchItemWriterBuilder<Map<String, Object>>()
                .itemSqlParameterSourceProvider(new ItemSqlParameterSourceProvider<Map<String,Object>>(){
                    @Override
                    public SqlParameterSource createSqlParameterSource(Map<String, Object> map) {
                        return new MapSqlParameterSource(map);
                    }
                })
                .sql("INSERT INTO "+tableName+" ("+columnNames+") VALUES ("+columnValues+")")
                .dataSource(dataSource).build();
    }

    @Bean
    public Job flatFileJob(Step step) {
        return jobBuilderFactory.get("flatFileJob")
        .incrementer(new RunIdIncrementer())
        .listener(new JobExecutionListenerSupport(){
            @Override
            public void afterJob(JobExecution jobExecution) {
                if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
                    log.info("!!! Job executed successfully");
                }
            }
        })
        .flow(step)
        .end()
        .build();
    }

    @Bean
    public Step step(FlatFileItemReader<Map<String, Object>> reader, JdbcBatchItemWriter<Map<String, Object>> writer) {
        return stepBuilderFactory
            .get("step")
            .<Map<String, Object>, Map<String, Object>>chunk(10)
            .reader(reader)
            .writer(writer)
            .build();
    }
}
