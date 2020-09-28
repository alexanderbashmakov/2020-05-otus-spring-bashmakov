package ru.otus.library.service;

import de.vandermeer.asciitable.AsciiTable;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.domain.Author;
import ru.otus.library.dto.AuthorDto;
import ru.otus.library.repository.AuthorRepository;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final IOService ioService;
    private final MessageBundleService messages;

    @Transactional
    @Override
    public void create(@NonNull String bookId, Author author) {
        authorRepository.create(bookId, author);
    }

    @Transactional
    @Override
    public void update(@NonNull String id, Author author) {
        authorRepository.update(id, author);
    }

    @Override
    public void printAll(Pageable pageable) {
        printAll(authorRepository.findAuthors(pageable));
    }

    @Override
    public void printAll(Pageable pageable, String bookId) {
        printAll(authorRepository.findAuthorsByBookId(pageable, bookId));
    }

    @Transactional(readOnly = true)
    @Override
    public void printAll(Page<AuthorDto> page) {
        AsciiTable table = new AsciiTable();
        table.addRule();
        table.addRow(messages.getMessage("author.id"), messages.getMessage("author.name"), messages.getMessage("book.name"));

        page.getContent().forEach(author -> {
            table.addRule();
            table.addRow(author.getId(), author.getName(), author.getBookName());
        });
        table.addRule();
        ioService.print(table.render());
        ioService.print(messages.getMessage("page", page.getNumber() + 1, page.getTotalPages()));
        ioService.print(messages.getMessage("total", page.getTotalElements()));
    }

    @Transactional
    @Override
    public void deleteById(String id) {
        authorRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteAll() {
        authorRepository.deleteAll();
    }
}
