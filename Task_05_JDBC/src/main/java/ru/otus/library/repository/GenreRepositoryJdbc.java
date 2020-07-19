package ru.otus.library.repository;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.library.domain.Genre;
import ru.otus.library.exceptions.EntityNotFound;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class GenreRepositoryJdbc implements GenreRepository {

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public Genre insert(@NonNull Genre genre) {
        MapSqlParameterSource ps = new MapSqlParameterSource().addValue("name", genre.getName());
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update("insert into genres (name) values (:name)", ps, keyHolder);
        long id = Objects.requireNonNull(keyHolder.getKey()).longValue();
        genre.setId(id);
        return genre;
    }

    @Override
    public void update(Genre genre) {
        jdbc.update("update genres set name = :name where id = :id", Map.of("id", genre.getId(), "name", genre.getName()));
    }

    @Override
    public void deleteById(Long id) {
        jdbc.update("delete from genres where id = :id", Map.of("id", id));
    }

    @Override
    public Optional<Genre> getById(Long id) {
        return jdbc.query("select id, name from genres where id = :id",
                Map.of("id", id), new GenreMapper()).stream().findFirst();
    }

    @Override
    public Optional<Genre> getByName(String name) {
        return jdbc.query("select id, name from genres where name = :name",
                Map.of("name", name), new GenreMapper()).stream().findFirst();
    }

    @Override
    public List<Genre> getAll() {
        return jdbc.query("select id, name from genres", new GenreMapper());
    }

    private static class GenreMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Genre(rs.getLong("id"), rs.getString("name"));
        }
    }
}
