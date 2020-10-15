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
import ru.otus.library.domain.Comment;
import ru.otus.library.dto.CommentDto;
import ru.otus.library.dto.CountDto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@DisplayName("CommentRepository:")
@Import(TestRepositoryConfig.class)
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

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
                .comments(new ArrayList<>())
                .build());
        Comment comment = Comment.builder().comment("Wow").created(new Date()).build();
        commentRepository.create(book.getId(), comment);
        Book foundBook = mongoTemplate.findOne(
                new Query(Criteria.where("_id").is(book.getId())
                .and("comments.comment").is(comment.getComment())
                .and("comments.created").is(comment.getCreated())
                ), Book.class);

        assert foundBook != null;
        assertThat(foundBook.getComments()).containsExactly(comment);
    }


   @Test
    @DisplayName("обновляет запись в БД update()")
    public void update() {
        Book book = mongoTemplate.insert(Book.builder()
                .name("TestBook")
                .comments(new ArrayList<>())
                .build());
        Comment comment = Comment.builder().comment("Wow").created(new Date()).build();
        commentRepository.create(book.getId(), comment);
        comment.setComment("UpdatedComment");
        comment.setCreated(new Date());
        commentRepository.update(comment.getId(), comment);
        Book foundBook = mongoTemplate.findOne(
                new Query(Criteria.where("_id").is(book.getId())
                        .and("comments.comment").is(comment.getComment())
                        .and("comments.created").is(comment.getCreated())
                ), Book.class);

        assert foundBook != null;
        assertThat(foundBook.getComments()).containsExactly(comment);
    }

    @Test
    @DisplayName("удаляет запись из БД deleteById()")
    public void deleteById() {
        Book book = mongoTemplate.insert(Book.builder()
                .name("TestBook")
                .comments(new ArrayList<>())
                .build());
        Comment comment = Comment.builder().comment("Wow").created(new Date()).build();
        commentRepository.create(book.getId(), comment);
        commentRepository.deleteById(comment.getId());
        Book foundBook = mongoTemplate.findOne(
                new Query(Criteria.where("_id").is(book.getId())
                        .and("comments.comment").is(comment.getComment())
                ), Book.class);

        assertThat(foundBook).isNull();
    }

    @Test
    @DisplayName("удаляет все записи deleteAll()")
    public void deleteAll() {
        Book book1 = mongoTemplate.insert(Book.builder()
                .name("TestBook1")
                .comments(new ArrayList<>())
                .build());
        Comment comment1 = Comment.builder().comment("Comment1").created(new Date()).build();
        commentRepository.create(book1.getId(), comment1);
        Book book2 = mongoTemplate.insert(Book.builder()
                .name("TestBook2")
                .comments(new ArrayList<>())
                .build());
        Comment comment2 = Comment.builder().comment("Comment2").created(new Date()).build();
        commentRepository.create(book2.getId(), comment2);

        commentRepository.deleteAll();
        Aggregation aggregation = Aggregation.newAggregation(Aggregation.unwind("comments"), Aggregation.count().as("total"));
        CountDto countDto = mongoTemplate.aggregate(aggregation, Book.class, CountDto.class).getUniqueMappedResult();
        assertThat(countDto).isNull();
    }

    @Test
    @DisplayName("получает все записи из БД findComments()")
    void getAll() {
        Book book = mongoTemplate.insert(Book.builder()
                .name("TestBook")
                .comments(new ArrayList<>())
                .build());
        Comment comment1 = Comment.builder().comment("Comment1").created(new Date()).build();
        commentRepository.create(book.getId(), comment1);
        Comment comment2 = Comment.builder().comment("Comment2").created(new Date()).build();
        commentRepository.create(book.getId(), comment2);
        List<CommentDto> comments = commentRepository.findCommentsByBookId(book.getId());
        assertThat(comments).extracting(CommentDto::getComment).contains(comment1.getComment()).contains(comment2.getComment());
    }
}