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
import ru.otus.library.domain.Author;
import ru.otus.library.dto.AuthorDto;
import ru.otus.library.repository.AuthorRepository;

import java.util.List;

import static org.mockito.Mockito.verify;

@DisplayName("Класс AuthorServiceImpl:")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AuthorServiceImpl.class)
class AuthorServiceImplTest {
    @Autowired
    private AuthorService service;

    @MockBean
    private AuthorRepository repository;

    @MockBean
    private MessageBundleService messageBundleService;

    @DisplayName("сохраняет автора create()")
    @Test
    void createAuthor() {
        String bookId = "1";
        Author author = Author.builder().name("testAuthor").build();
        service.create(bookId, author);
        verify(repository).create(bookId, author);
    }

    @DisplayName("обновляет автора update()")
    @Test
    void updateAuthor() {
        String id = "1";
        Author author = Author.builder().name("testAuthor").build();
        service.update(id, author);
        verify(repository).update(id, author);
    }

    @DisplayName("удаляет запись по id deleteById()")
    @Test
    void deleteById() {
        service.deleteById("1L");
        verify(repository).deleteById("1L");
    }

    @DisplayName("удаляет все записи deleteAll()")
    @Test
    void deleteAll() {
        service.deleteAll();
        verify(repository).deleteAll();
    }
}