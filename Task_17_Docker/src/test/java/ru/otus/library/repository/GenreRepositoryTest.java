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
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;
import ru.otus.library.dto.CountDto;
import ru.otus.library.dto.GenreDto;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@DisplayName("GenreRepository:")
@Import(TestRepositoryConfig.class)
class GenreRepositoryTest {

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    void setUp() {
        mongoTemplate.dropCollection(Book.class);
    }

    @Test
    @DisplayName("добавляет запись в БД create()")
    public void insert() {
        Book book = mongoTemplate.insert(Book.builder()
                .name("TestBook")
                .genres(new ArrayList<>())
                .build());
        Genre genre = Genre.builder().name("TestGenre").build();
        genreRepository.create(book.getId(), genre);
        Book foundBook = mongoTemplate.findOne(new Query(Criteria.where("_id").is(book.getId()).and("genres.name").is(genre.getName())), Book.class);

        assert foundBook != null;
        assertThat(foundBook.getGenres()).extracting(Genre::getName).contains(genre.getName());
    }


    @Test
    @DisplayName("обновляет запись в БД update()")
    public void update() {
        Book book = mongoTemplate.insert(Book.builder()
                .name("TestBook")
                .genres(new ArrayList<>())
                .build());
        Genre genre = Genre.builder().name("TestGenre").build();
        genreRepository.create(book.getId(), genre);
        genre.setName("UpdatedGenre");
        genreRepository.update(genre.getId(), genre);
        Book foundBook = mongoTemplate.findOne(new Query(Criteria.where("_id").is(book.getId()).and("genres.name").is(genre.getName())), Book.class);

        assert foundBook != null;
        assertThat(foundBook.getGenres()).extracting(Genre::getName).contains(genre.getName());
    }

    @Test
    @DisplayName("удаляет запись из БД deleteById()")
    public void deleteById() {
        Book book = mongoTemplate.insert(Book.builder()
                .name("TestBook")
                .genres(new ArrayList<>())
                .build());
        Genre genre = Genre.builder().name("TestGenre").build();
        genreRepository.create(book.getId(), genre);

        genreRepository.deleteById(genre.getId());
        Book foundBook = mongoTemplate.findOne(new Query(Criteria.where("_id").is(book.getId()).and("genres.name").is(genre.getName())), Book.class);
        assertThat(foundBook).isNull();
    }

    @Test
    @DisplayName("удаляет все записи deleteAll()")
    public void deleteAll() {
        Book book1 = mongoTemplate.insert(Book.builder()
                .name("TestBook")
                .genres(new ArrayList<>())
                .build());
        Genre genre1 = Genre.builder().name("Genre1").build();
        genreRepository.create(book1.getId(), genre1);
        Book book2 = mongoTemplate.insert(Book.builder()
                .name("TestBook")
                .genres(new ArrayList<>())
                .build());
        Genre genre2 = Genre.builder().name("Genre2").build();
        genreRepository.create(book2.getId(), genre2);

        genreRepository.deleteAll();

        Aggregation aggregation = Aggregation.newAggregation(Aggregation.unwind("genres"), Aggregation.count().as("total"));
        CountDto countDto = mongoTemplate.aggregate(aggregation, Book.class, CountDto.class).getUniqueMappedResult();
        assertThat(countDto).isNull();
    }

    @Test
    @DisplayName("получает все записи из БД findGenres()")
    void getAll() {
        Book book = mongoTemplate.insert(Book.builder()
                .name("TestBook")
                .authors(new ArrayList<>())
                .build());
        Genre genre1 = Genre.builder().name("Genre1").build();
        genreRepository.create(book.getId(), genre1);
        Genre genre2 = Genre.builder().name("Genre2").build();
        genreRepository.create(book.getId(), genre2);
        List<GenreDto> genres = genreRepository.findGenresByBookId(book.getId());
        assertThat(genres).extracting(GenreDto::getName).contains(genre1.getName()).contains(genre2.getName());
    }
}