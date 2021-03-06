package ru.otus.library.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.library.dto.AuthorDto;
import ru.otus.library.service.AuthorService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthorControllerRest {

    private final AuthorService service;

    @GetMapping(value = "/api/author/{bookId}")
    public List<AuthorDto> authorList(@PathVariable String bookId) {
        return service.findAllByBook(bookId);
    }

    @PostMapping(value = "/api/author/{bookId}")
    public ResponseEntity<AuthorDto> authorSave(@PathVariable String bookId, @RequestBody AuthorDto author) {
        service.create(bookId, AuthorDto.toDomainObject(author));
        return ResponseEntity.status(HttpStatus.CREATED).body(author);
    }

    @PutMapping(value = "/api/author/{id}")
    public ResponseEntity<AuthorDto> authorUpdate(@PathVariable String id, @RequestBody AuthorDto author){
        service.update(id, AuthorDto.toDomainObject(author));
        return ResponseEntity.ok(author);
    }

    @DeleteMapping(value = "/api/author/{id}")
    public ResponseEntity<Void> authorDelete(@PathVariable String id) {
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
