package ru.otus.library.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.dto.AuthorDto;
import ru.otus.library.dto.CountDto;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@DisplayName("AuthorRepository:")
@Import(TestRepositoryConfig.class)
class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    void setUp() {
        mongoTemplate.dropCollection(Book.class);
    }

    @Test
    @DisplayName("добавляет запись в БД")
    public void insert() {
        Book book = mongoTemplate.insert(Book.builder()
                .name("TestBook")
                .authors(new ArrayList<>())
                .build());
        Author author = Author.builder().name("TestAuthor").build();
        authorRepository.create(book.getId(), author);
        Book foundBook = mongoTemplate.findOne(new Query(Criteria.where("_id").is(book.getId()).and("authors.name").is(author.getName())), Book.class);

        assert foundBook != null;
        assertThat(foundBook.getAuthors()).extracting(Author::getName).contains(author.getName());
    }


    @Test
    @DisplayName("обновляет запись в БД")
    public void update() {
        Book book = mongoTemplate.insert(Book.builder()
                .name("TestBook")
                .authors(new ArrayList<>())
                .build());
        Author author = Author.builder().name("TestAuthor").build();
        authorRepository.create(book.getId(), author);
        author.setName("UpdatedAuthor");
        authorRepository.update(author.getId(), author);
        Book foundBook = mongoTemplate.findOne(new Query(Criteria.where("_id").is(book.getId()).and("authors.name").is(author.getName())), Book.class);

        assert foundBook != null;
        assertThat(foundBook.getAuthors()).extracting(Author::getName).contains(author.getName());
    }

    @Test
    @DisplayName("удаляет запись из БД по Id")
    public void deleteById() {
        Book book = mongoTemplate.insert(Book.builder()
                .name("TestBook")
                .authors(new ArrayList<>())
                .build());
        Author author = Author.builder().name("TestAuthor").build();
        authorRepository.create(book.getId(), author);

        authorRepository.deleteById(author.getId());
        Book foundBook = mongoTemplate.findOne(new Query(Criteria.where("_id").is(book.getId()).and("authors.name").is(author.getName())), Book.class);
        assertThat(foundBook).isNull();
    }

    @Test
    @DisplayName("удаляет все записи")
    public void deleteAll() {
        Book book1 = mongoTemplate.insert(Book.builder()
                .name("TestBook1")
                .authors(new ArrayList<>())
                .build());
        Author author1 = Author.builder().name("Author1").build();
        authorRepository.create(book1.getId(), author1);
        Book book2 = mongoTemplate.insert(Book.builder()
                .name("TestBook2")
                .authors(new ArrayList<>())
                .build());
        Author author2 = Author.builder().name("Author2").build();
        authorRepository.create(book2.getId(), author2);

        authorRepository.deleteAll();

        Aggregation aggregation = Aggregation.newAggregation(Aggregation.unwind("authors"), Aggregation.count().as("total"));
        CountDto countDto = mongoTemplate.aggregate(aggregation, Book.class, CountDto.class).getUniqueMappedResult();
        assertThat(countDto).isNull();
    }

    @Test
    @DisplayName("получает все записи из БД")
    void getAll() {
        Book book = mongoTemplate.insert(Book.builder()
                .name("TestBook")
                .authors(new ArrayList<>())
                .build());
        Author author1 = Author.builder().name("Author1").build();
        authorRepository.create(book.getId(), author1);
        Author author2 = Author.builder().name("Author2").build();
        authorRepository.create(book.getId(), author2);
        Page<AuthorDto> page = authorRepository.findAuthors(PageRequest.of(0, 5));
        assertThat(page.getContent()).extracting(AuthorDto::getName).contains(author1.getName()).contains(author2.getName());
    }
}