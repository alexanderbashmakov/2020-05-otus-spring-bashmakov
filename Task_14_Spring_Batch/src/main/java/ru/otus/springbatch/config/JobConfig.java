package ru.otus.springbatch.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.springbatch.model.Book;
import ru.otus.springbatch.model.BookDst;
import ru.otus.springbatch.service.BookConverter;

import javax.persistence.EntityManagerFactory;
import java.util.Map;


@Configuration
public class JobConfig {
    private static final int CHUNK_SIZE = 1;
    private final Logger logger = LoggerFactory.getLogger("Batch");

    public static final String IMPORT_BOOK_JOB_NAME = "importBookJob";

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job importBookJob(@Qualifier("importBooks") Step importBooks) {
        return jobBuilderFactory.get(IMPORT_BOOK_JOB_NAME)
                .incrementer(new RunIdIncrementer())
                .flow(importBooks)
                .end()
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(JobExecution jobExecution) {
                        logger.info("Начало job");
                    }

                    @Override
                    public void afterJob(JobExecution jobExecution) {
                        logger.info("Конец job");
                    }
                })
                .build();
    }

    @Bean
    public Step importBooks(ItemReader<Book> mongoBookReader,
                            ItemProcessor<Book, BookDst> bookProcessor,
                            ItemWriter<BookDst> bookWriter) {
        return stepBuilderFactory.get("importBooks")
                .<Book, BookDst>chunk(CHUNK_SIZE)
                .reader(mongoBookReader)
                .processor(bookProcessor)
                .writer(bookWriter)
                .build();
    }

    @Bean
    public MongoItemReader<Book> reader(MongoTemplate template) {
        MongoItemReader<Book> mongoItemReader = new MongoItemReader<>();
        mongoItemReader.setTemplate(template);
        mongoItemReader.setName("mongoBookReader");
        mongoItemReader.setQuery("{}");
        mongoItemReader.setSort(Map.of("_id", Sort.Direction.ASC));
        mongoItemReader.setTargetType(Book.class);
        return mongoItemReader;
    }

    @Bean
    public ItemProcessor<Book, BookDst> bookProcessor(BookConverter bookConverter) {
        return bookConverter::convertBook;
    }

    @Bean
    public ItemWriter<BookDst> bookWriter(EntityManagerFactory emf) {
        JpaItemWriter<BookDst> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(emf);
        return writer;
    }
}
