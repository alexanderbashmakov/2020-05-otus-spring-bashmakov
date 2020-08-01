package ru.otus.library.service;

import de.vandermeer.asciitable.AsciiTable;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.domain.Author;
import ru.otus.library.repository.AuthorRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final IOService ioService;
    private final MessageBundleService messages;

    @Transactional
    @Override
    public void save(Author author) {
        authorRepository.save(author);
    }

    @Transactional(readOnly = true)
    @Override
    public void printAll() {
        List<Author> authors = authorRepository.getAll();
        AsciiTable table = new AsciiTable();
        table.addRule();
        table.addRow(messages.getMessage("author.id"), messages.getMessage("author.name"));

        authors.forEach(author -> {
            table.addRule();
            table.addRow(author.getId(), author.getName());
        });
        table.addRule();
        ioService.print(table.render());
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        authorRepository.deleteById(id);
    }
}
