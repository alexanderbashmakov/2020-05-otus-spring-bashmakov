package ru.otus.library.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.library.domain.Book;
import ru.otus.library.dto.BookDto;
import ru.otus.library.exceptions.EntityNotFound;
import ru.otus.library.repository.BookRepository;
import ru.otus.library.service.BookService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService service;

    @RequestMapping(value="/book/{bookId}", method = RequestMethod.GET)
    public BookDto get(@PathVariable String bookId) {
        return service.findById(bookId);
    }

    @RequestMapping(value="/book", method = RequestMethod.GET)
    public List<BookDto> getAll() {
        return service.findAll();
    }

    @RequestMapping(value = "/book", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<BookDto> create(@RequestBody BookDto bookDto) {
        Book book = BookDto.toDomainObject(bookDto);
        service.save(book);
        return ResponseEntity.ok(BookDto.toDto(book));
    }

    @RequestMapping("/badRequest")
    public ResponseEntity error() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/book/{bookId}")
    public ResponseEntity<String> deleteById(@PathVariable String bookId) {
        service.deleteById(bookId);
        return ResponseEntity.ok("OK");
    }

    @DeleteMapping("/book")
    public ResponseEntity<String> deleteAll() {
        service.deleteAll();
        return ResponseEntity.ok("OK");
    }

    @RequestMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("OK");
    }

    @ExceptionHandler(EntityNotFound.class)
    public ResponseEntity<String> handleNotEnoughFunds(EntityNotFound ex) {
        return ResponseEntity.badRequest().body("Not found");
    }
}
