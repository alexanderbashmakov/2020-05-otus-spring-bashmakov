package ru.otus.springbatch.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.springbatch.model.Book;
import ru.otus.springbatch.model.BookDst;
import ru.otus.springbatch.model.Genre;
import ru.otus.springbatch.model.GenreTmp;
import ru.otus.springbatch.reader.AggregateMongoReader;
import ru.otus.springbatch.service.BookConverter;
import ru.otus.springbatch.service.GenreConverter;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
@Configuration
public class JobConfig {
    private static final int CHUNK_SIZE = 5;
    private final Logger logger = LoggerFactory.getLogger("Batch");

    public static final String IMPORT_BOOK_JOB_NAME = "importBookJob";

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job importBookJob(
            @Qualifier("importTmpGenres") Step importTmpGenres,
            @Qualifier("importBooks") Step importBooks,
            @Qualifier("dropTempTables") Step dropTempTables) {
        return jobBuilderFactory.get(IMPORT_BOOK_JOB_NAME)
                .incrementer(new RunIdIncrementer())
                .start(importTmpGenres)
                .next(importBooks)
                .next(dropTempTables)
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
    public Step importTmpGenres(AggregateMongoReader<Genre> genreReader,
                                @Qualifier("genreTmpItemProcessor") ItemProcessor<Genre, GenreTmp> genreGenreTmpItemProcessor,
                                @Qualifier("tmpGenreWriter") ItemWriter<GenreTmp> tmpGenreWriter) {
        return stepBuilderFactory.get("importTmpGenres")
                .<Genre, GenreTmp>chunk(CHUNK_SIZE)
                .reader(genreReader)
                .processor(genreGenreTmpItemProcessor)
                .writer(tmpGenreWriter)
                .build();
    }
    @Bean
    public ItemProcessor<Genre, GenreTmp> genreTmpItemProcessor(GenreConverter genreConverter) {
        return genreConverter::convertGenre;
    }

    @Bean
    public AggregateMongoReader<Genre> genreReader(MongoTemplate mongoTemplate) {
        final AggregateMongoReader<Genre> reader = new AggregateMongoReader<>(mongoTemplate, "genres", Genre.class);
        reader.setName("genreReader");
        return reader;
    }

    @Bean
    public ItemWriter<GenreTmp> tmpGenreWriter(EntityManagerFactory emf) {
        JpaItemWriter<GenreTmp> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(emf);
        return writer;
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

    @Bean
    @Transactional
    public Step dropTempTables(EntityManagerFactory emf) {
        return stepBuilderFactory.get("dropTempTables").tasklet((stepContribution, chunkContext) -> {
            String query = "DROP TABLE tmp_genres";
            EntityManager entityManager = emf.createEntityManager();
            entityManager.joinTransaction();
            entityManager.createNativeQuery(query).executeUpdate();
            return RepeatStatus.FINISHED;
        }).build();
    }
}
