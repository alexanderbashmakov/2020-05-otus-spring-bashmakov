package ru.otus.library.service;

import de.vandermeer.asciitable.AsciiTable;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.domain.Genre;
import ru.otus.library.dto.GenreDto;
import ru.otus.library.repository.GenreRepository;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;
    private final IOService ioService;
    private final MessageBundleService messages;

    @Transactional
    @Override
    public void create(@NonNull String bookId, Genre genre) {
        genreRepository.create(bookId, genre);
    }

    @Override
    public void update(@NonNull String id, Genre genre) {
        genreRepository.update(id, genre);
    }

    @Transactional(readOnly = true)
    @Override
    public void printAll(Page<GenreDto> page) {
        AsciiTable table = new AsciiTable();
        table.addRule();
        table.addRow(messages.getMessage("genre.id"), messages.getMessage("genre.name"), messages.getMessage("book.name"));

        page.getContent().forEach(genre -> {
            table.addRule();
            table.addRow(genre.getId(), genre.getName(), genre.getBookName());
        });
        table.addRule();
        ioService.print(table.render());
        ioService.print(messages.getMessage("page", page.getNumber() + 1, page.getTotalPages()));
        ioService.print(messages.getMessage("total", page.getTotalElements()));
    }

    @Override
    public void printAll(Pageable pageable) {
        printAll(genreRepository.findGenres(pageable));
    }

    @Override
    public void printAll(Pageable pageable, String bookId) {
        printAll(genreRepository.findGenresByBookId(pageable, bookId));
    }

    @Transactional
    @Override
    public void deleteById(String id) {
        genreRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        genreRepository.deleteAll();
    }
}
