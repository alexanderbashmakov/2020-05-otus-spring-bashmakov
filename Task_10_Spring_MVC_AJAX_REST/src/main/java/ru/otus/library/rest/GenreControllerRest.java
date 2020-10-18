package ru.otus.library.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.library.dto.GenreDto;
import ru.otus.library.service.GenreService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GenreControllerRest {

    private final GenreService service;

    @GetMapping(value = "/api/genres/{bookId}")
    public List<GenreDto> genreList(@PathVariable String bookId) {
        return service.findAllByBook(bookId);
    }

    @PostMapping(value = "/api/genre/{bookId}")
    public ResponseEntity<GenreDto> genreSave(@PathVariable String bookId, @RequestBody GenreDto genre) {
        service.create(bookId, GenreDto.toDomainObject(genre));
        return ResponseEntity.status(HttpStatus.CREATED).body(genre);
    }

    @PutMapping(value = "/api/genre/{id}")
    public ResponseEntity<GenreDto> genreUpdate(@PathVariable String id, @RequestBody GenreDto genre){
        service.update(id, GenreDto.toDomainObject(genre));
        return ResponseEntity.ok(genre);
    }

    @DeleteMapping(value = "/api/genre/{id}")
    public ResponseEntity<Void> deleteGenre(@PathVariable String id) {
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
