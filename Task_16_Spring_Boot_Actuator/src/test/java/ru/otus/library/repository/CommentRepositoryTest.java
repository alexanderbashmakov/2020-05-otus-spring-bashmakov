package ru.otus.library.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.domain.Genre;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(TestRepositoryConfig.class)
@DisplayName("CommentRepository:")
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("добавляет запись в БД insert()")
    public void insert() {
        Comment comment = Comment.builder().commentStr("TestComment").build();
        commentRepository.save(comment);
        Comment createdComment = em.find(Comment.class, comment.getId());
        assertThat(comment).isEqualToComparingFieldByField(createdComment);
    }

    @Test
    @DisplayName("обновляет запись в БД update()")
    public void update() {
        Comment comment = Comment.builder().commentStr("TestComment").build();
        em.persist(comment);
        comment.setCommentStr("Updated");
        commentRepository.save(comment);
        assertThat(em.find(Comment.class, comment.getId())).isEqualToComparingFieldByField(comment);
    }

    @Test
    @DisplayName("удаляет запись из БД deleteById()")
    public void deleteById() {
        Comment comment = Comment.builder().commentStr("CommentForDelete").build();
        em.persist(comment);
        em.detach(comment);

        commentRepository.deleteById(comment.getId());
        val deletedComment = em.find(Genre.class, comment.getId());

        assertThat(deletedComment).isNull();
    }

    @Test
    @DisplayName("получает все записи из БД getAll()")
    void getAll() {
        Comment comment1 = createComment("Comment1", "Book1", "Author1", "Genre1");
        em.persist(comment1);
        Comment comment2 = createComment("Comment2", "Book2", "Author2", "Genre2");
        em.persist(comment2);
        assertThat(commentRepository.findAll()).containsExactly(comment1, comment2);
    }

    @Test
    @DisplayName("получает запись по id из БД getById()")
    void getById() {
        Comment comment = Comment.builder().commentStr("newComment").build();
        em.persist(comment);
        assertThat(commentRepository.findById(comment.getId())).get().isEqualToComparingFieldByField(comment);
    }

    private Comment createComment(String commentStr, String bookName, String authorName, String genreName) {
        return Comment.builder()
                .commentStr(commentStr)
                .book(Book.builder()
                        .name(bookName)
                        .author(Author.builder().name(authorName).build())
                        .genre(Genre.builder().name(genreName).build())
                        .build())
                .build();
    }
}