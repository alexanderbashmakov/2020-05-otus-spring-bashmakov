package ru.otus.library.service;

import de.vandermeer.asciitable.AsciiTable;
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
    private IOService ioService;

    @MockBean
    private MessageBundleService messageBundleService;

    @DisplayName("сохраняет автора")
    @Test
    void createAuthor() {
        String bookId = "1";
        Author author = Author.builder().name("testAuthor").build();
        service.create(bookId, author);
        verify(repository).create(bookId, author);
    }

    @DisplayName("обновляет автора")
    @Test
    void updateAuthor() {
        String id = "1";
        Author author = Author.builder().name("testAuthor").build();
        service.update(id, author);
        verify(repository).update(id, author);
    }

    @DisplayName("выводит все записи")
    @Test
    void printAll() {
        Mockito.when(messageBundleService.getMessage("author.id")).thenReturn("author_id");
        Mockito.when(messageBundleService.getMessage("author.name")).thenReturn("author_name");
        Mockito.when(messageBundleService.getMessage("book.name")).thenReturn("book_name");

        List<AuthorDto> authors = List.of(AuthorDto.builder().id("1L").bookId("10").bookName("Book").name("author").build());
        PageRequest pageRequest = PageRequest.of(0, authors.size());
        PageImpl<AuthorDto> page = new PageImpl<>(authors, pageRequest, authors.size());


        Mockito.when(repository.findAuthors(pageRequest)).thenReturn(page);

        AsciiTable table = new AsciiTable();
        table.addRule();
        table.addRow(messageBundleService.getMessage("author.id"), messageBundleService.getMessage("author.name"), messageBundleService.getMessage("book.name"));
        authors.forEach(author -> {
            table.addRule();
            table.addRow(author.getId(), author.getName(), author.getBookName());
        });
        table.addRule();

        service.printAll(pageRequest);
        verify(ioService).print(table.render());
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