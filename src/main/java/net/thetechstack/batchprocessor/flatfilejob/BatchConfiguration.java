package net.thetechstack.batchprocessor.flatfilejob;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.database.ItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

@Configuration
public class BatchConfiguration {
    @Autowired public JobBuilderFactory jobBuilderFactory;
    @Autowired public StepBuilderFactory stepBuilderFactory;
   
    @Bean
    @StepScope
    public FlatFileItemReader<Map<String, Object>> reader(@Value("#{jobParameters['input-file']}") String inputFile,
                @Value("#{jobParameters['delimiter']}") String delimiter,
                @Value("#{jobParameters['columns']}") String columns) {
        FlatFileItemReader<Map<String, Object>> itemReader = new FlatFileItemReader<>();
        itemReader.setLinesToSkip(0);
        itemReader.setName("item-reader");
        itemReader.setResource(new FileSystemResource(inputFile));
        DefaultLineMapper<Map<String, Object>> mapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer(delimiter);
        tokenizer.setNames(columns.split(","));
        mapper.setLineTokenizer(tokenizer);
        mapper.setFieldSetMapper(new CustomFieldSetMapper());
        itemReader.setLineMapper(mapper);
        return itemReader;
    }

    @Bean
    public JdbcBatchItemWriter<Map<String, Object>> jdbcBatchItemWriter(DataSource dataSource) {
    return new JdbcBatchItemWriterBuilder<Map<String, Object>>()
        .itemSqlParameterSourceProvider(new MapSqlParameterSourceProvider())
        .sql("INSERT INTO book (book_id, good_reads_book_id) VALUES (:bookId, :goodReadsBookId)")
        .dataSource(dataSource)
        .build();
    }

class MapSqlParameterSourceProvider implements ItemSqlParameterSourceProvider<Map<String, Object>> {

    @Override
    public SqlParameterSource createSqlParameterSource(Map<String, Object> map) {
        SqlParameterSource parameters = new MapSqlParameterSource().addValue("bookId", map.get("bookId"))
        .addValue("goodReadsBookId", map.get("goodReadsBookId"));
        return parameters;
    }
    
}
    @Bean
    public Job flatFileJob(JobCompletionNotificationListener listener, Step step1) {
    return jobBuilderFactory.get("flatFileJob")
        .incrementer(new RunIdIncrementer())
        .listener(listener)
        .flow(step1)
        .end()
        .build();
    }

    @Bean
    public Step step1(FlatFileItemReader<Map<String, Object>> reader, JdbcBatchItemWriter<Map<String, Object>> jdbcBatchItemWriter) {
    return stepBuilderFactory.get("step1")
        .<Map<String, Object>, Map<String, Object>> chunk(10)
        .reader(reader)
        .writer(jdbcBatchItemWriter)
        .build();
    }
}
