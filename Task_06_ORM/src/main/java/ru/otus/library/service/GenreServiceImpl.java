package ru.otus.library.service;

import de.vandermeer.asciitable.AsciiTable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.domain.Genre;
import ru.otus.library.repository.GenreRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;
    private final IOService ioService;
    private final MessageBundleService bundleService;

    @Transactional
    @Override
    public void save(Genre genre) {
        genreRepository.save(genre);
    }

    @Transactional(readOnly = true)
    @Override
    public void printAll() {
        List<Genre> genres = genreRepository.getAll();
        AsciiTable table = new AsciiTable();
        table.addRule();
        table.addRow(bundleService.getMessage("genre.id"), bundleService.getMessage("genre.name"));

        genres.forEach(genre -> {
            table.addRule();
            table.addRow(genre.getId(), genre.getName());
        });
        table.addRule();
        ioService.print(table.render());
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        genreRepository.deleteById(id);
    }
}
