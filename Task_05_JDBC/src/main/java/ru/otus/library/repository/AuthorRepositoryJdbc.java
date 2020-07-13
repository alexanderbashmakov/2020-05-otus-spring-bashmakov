package ru.otus.library.repository;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.library.domain.Author;
import ru.otus.library.exceptions.EntityNotFound;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class AuthorRepositoryJdbc implements AuthorRepository {

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public Author insert(@NonNull Author author) {
        MapSqlParameterSource ps = new MapSqlParameterSource().addValue("name", author.getName());
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update("insert into authors (name) values (:name)", ps, keyHolder);
        Long id = Objects.requireNonNull(keyHolder.getKey()).longValue();
        author.setId(id);
        return author;
    }

    @Override
    public Author update(Author author) {
        Author updAuthor = getById(Objects.requireNonNull(author.getId())).orElseThrow(EntityNotFound::new);
        updAuthor.setName(author.getName());
        jdbc.update("update authors set name = :name where id = :id", Map.of("id", author.getId(), "name", author.getName()));
        return updAuthor;
    }

    @Override
    public void deleteById(Long id) {
        jdbc.update("delete from authors where id = :id", Map.of("id", id));
    }

    @Override
    public Optional<Author> getById(Long id) {
        return jdbc.query("select id, name from authors where id = :id",
                Map.of("id", id), new AuthorMapper()).stream().findFirst();
    }

    @Override
    public Optional<Author> getByName(String name) {
        return jdbc.query("select id, name from authors where name = :name",
                Map.of("name", name), new AuthorMapper()).stream().findFirst();
    }

    @Override
    public List<Author> getAll() {
        return jdbc.query("select * from authors", new AuthorMapper());
    }

    private static class AuthorMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Author(rs.getLong("id"), rs.getString("name"));
        }
    }
}
