package ru.otus.library.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.library.domain.Comment;
import ru.otus.library.dto.CommentDto;
import ru.otus.library.repository.CommentRepository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.verify;

@DisplayName("Класс CommentServiceImpl:")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CommentServiceImpl.class)
class CommentServiceImplTest {
    @Autowired
    private CommentService service;

    @MockBean
    private CommentRepository repository;

    @MockBean
    private IOService ioService;

    @MockBean
    private MessageBundleService messageBundleService;

    @DisplayName("сохраняет комментарий")
    @Test
    void createGenre() {
        String bookId = "1";
        Comment comment = Comment.builder().comment("testComment").build();
        service.create(bookId, comment);
        verify(repository).create(bookId, comment);
    }

    @DisplayName("обновляет коммнетарий")
    @Test
    void updateGenre() {
        String id = "1";
        Comment comment = Comment.builder().comment("testComment").build();
        service.update(id, comment);
        verify(repository).update(id, comment);
    }

    @DisplayName("выводит все записи")
    @Test
    void printAll() {
        Mockito.when(messageBundleService.getMessage("comment.id")).thenReturn("comment_id");
        Mockito.when(messageBundleService.getMessage("comment.commentStr")).thenReturn("comment_comment");
        Mockito.when(messageBundleService.getMessage("comment.book.name")).thenReturn("book_name");
        Mockito.when(messageBundleService.getMessage("comment.created")).thenReturn("comment_created");

        List<CommentDto> comments = List.of(CommentDto.builder().id("1L").bookId("10").bookName("Book").comment("comment").created(new Date()).build());
        PageRequest pageRequest = PageRequest.of(0, comments.size());
        PageImpl<CommentDto> page = new PageImpl<>(comments, pageRequest, comments.size());


        Mockito.when(repository.findComments(pageRequest)).thenReturn(page);

    }

    @DisplayName("удаляет запись по id")
    @Test
    void deleteById() {
        service.deleteById("1L");
        verify(repository).deleteById("1L");
    }

    @DisplayName("удаляет все записи")
    @Test
    void deleteAll() {
        service.deleteAll();
        verify(repository).deleteAll();
    }
}