package ru.otus.library.repository;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;
import ru.otus.library.exceptions.EntityNotFound;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookRepositoryJdbc implements BookRepository {

    private final NamedParameterJdbcOperations jdbc;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    @Override
    public int count() {
        return jdbc.getJdbcOperations().queryForObject("select count(*) from books", Integer.class);
    }

    @Override
    public Book insert(@NonNull Book book) {
        book.setAuthor(authorRepository.getByName(book.getAuthor().getName()).orElseGet(() -> authorRepository.insert(
                Author.builder().name(book.getAuthor().getName()).build())));
        book.setGenre(genreRepository.getByName(book.getGenre().getName()).orElseGet(() -> genreRepository.insert(
                Genre.builder().name(book.getGenre().getName()).build())));
        MapSqlParameterSource ps = new MapSqlParameterSource().
                addValue("name", book.getName()).
                addValue("author_id", book.getAuthor().getId()).
                addValue("genre_id", book.getGenre().getId());
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update("insert into books (name, author_id, genre_id) values (:name, :author_id, :genre_id)", ps, keyHolder);
        Long id = Objects.requireNonNull(keyHolder.getKey()).longValue();
        book.setId(id);
        return book;
    }

    @Override
    public void update(Book book) {
        Book updBook = getById(book.getId()).orElseThrow(EntityNotFound::new);
        Author updAuthor = authorRepository.getByName(book.getAuthor().getName()).orElseGet(() -> authorRepository.insert(
                Author.builder().name(book.getAuthor().getName()).build()));
        Genre updGenre = genreRepository.getByName(book.getGenre().getName()).orElseGet(() -> genreRepository.insert(
                Genre.builder().name(book.getGenre().getName()).build()));
        updBook.setName(book.getName());
        updBook.setAuthor(updAuthor);
        updBook.setGenre(updGenre);
        jdbc.update("update books set name = :name, author_id = :author_id, genre_id = :genre_id where id = :id",
                Map.of("id", book.getId(), "name", book.getName(),
                        "author_id", updAuthor.getId(),
                        "genre_id", updGenre.getId())
        );
    }

    @Override
    public void deleteById(long id) {
        jdbc.update("delete from books where id = :id", Map.of("id", id));
    }

    @Override
    public Optional<Book> getById(long id) {
        return jdbc.query("select b.id id, b.name name, a.id author_id, a.name author_name, g.id genre_id, g.name genre_name from books b " +
                "left join authors a on a.id = b.author_id " +
                "left join genres g on g.id = b.genre_id " +
                "where b.id = :id", Map.of("id", id), new BookMapper()).stream().findFirst();
    }

    @Override
    public Optional<Book> getByName(String name) {
        return jdbc.query("select b.id id, b.name name, a.id author_id, a.name author_name, g.id genre_id, g.name genre_name from books b " +
                "left join authors a on a.id = b.author_id " +
                "left join genres g on g.id = b.genre_id " +
                "where b.name = :name", Map.of("name", name), new BookMapper()).stream().findFirst();
    }

    @Override
    public List<Book> getAll() {
        return jdbc.query("select b.id id, b.name name, a.id author_id, a.name author_name, g.id genre_id, g.name genre_name from books b " +
                "left join authors a on a.id = b.author_id " +
                "left join genres g on g.id = b.genre_id ", new BookMapper());
    }

    private static class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Book(resultSet.getLong("id"), resultSet.getString("name"),
                    new Author(resultSet.getLong("author_id"), resultSet.getString("author_name")),
                    new Genre(resultSet.getLong("genre_id"), resultSet.getString("genre_name")));
        }
    }
}
