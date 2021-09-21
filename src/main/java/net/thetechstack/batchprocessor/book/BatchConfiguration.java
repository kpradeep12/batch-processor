package net.thetechstack.batchprocessor.book;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
    @Autowired public JobBuilderFactory jobBuilderFactory;
    @Autowired public StepBuilderFactory stepBuilderFactory;

    @Bean
    public FlatFileItemReader<Book> reader() {
        return new FlatFileItemReaderBuilder<Book>()
            .name("bookReader")
            .resource(new FileSystemResource("/Users/pradeep/OneDrive/code/books.csv"))
            .delimited()
            .names(new String[] {"bookId","goodReadsBookId", "bestBookId", "workId", "booksCount", "isbn", "isbn13", "authors", "originalPublicationYear"
            , "originalTitle", "title", "languageCode", "averageRating", "ratingsCount", "workRatingsCount", "workTextReviewCount", "ratings1", "ratings2", "ratings3", "ratings4", "ratings5", "imageUrl", "smallImageUrl"})
            .fieldSetMapper(new BeanWrapperFieldSetMapper<Book>() {{
                setTargetType(Book.class);
            }})
            .build();
    }

    @Bean
    public MongoItemWriter<Book> writer(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<Book>().template(mongoTemplate).collection("books")
                .build();
    }

    @Bean
    public Job importUserJob(JobCompletionNotificationListener listener, Step step1) {
    return jobBuilderFactory.get("importUserJob")
        .incrementer(new RunIdIncrementer())
        .listener(listener)
        .flow(step1)
        .end()
        .build();
    }

    @Bean
    public Step step1(FlatFileItemReader<Book> reader, MongoItemWriter<Book> writer) {
    return stepBuilderFactory.get("step1")
        .<Book, Book> chunk(10)
        .reader(reader())
        .writer(writer)
        .build();
    }
}
