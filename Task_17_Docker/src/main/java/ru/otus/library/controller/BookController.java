package ru.otus.library.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.otus.library.domain.Book;
import ru.otus.library.dto.BookDto;
import ru.otus.library.exceptions.EntityNotFound;
import ru.otus.library.repository.BookRepository;
import ru.otus.library.service.BookService;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService service;

    @GetMapping(value="/")
    public ModelAndView bookList(Model model,
                                 @RequestParam(value = "page", defaultValue = "0") Integer curPage,
                                 @RequestParam(value = "size", defaultValue = "5") Integer size) {
        ModelAndView mvc = new ModelAndView("bookList");
        Page<BookDto> page = service.findAll(PageRequest.of(curPage, size));
        PageUtils.addPageParams(mvc, page);
        return mvc;
    }

    @GetMapping(value="/book")
    public ModelAndView get(@RequestParam(value = "id", required = false) String bookId) {
        ModelAndView modelAndView = new ModelAndView("book");
        if (bookId != null) {
            BookDto bookDto = service.findById(bookId);
            modelAndView.addObject("book", bookDto);
        } else {
            modelAndView.addObject("book", new BookDto());
        }
        return modelAndView;
    }

    @PostMapping(value = "/edit")
    public String create(@RequestParam(value = "id", required = false) String bookId, BookDto bookDto) {
        Book book = BookDto.toDomainObject(bookDto);
        if (bookId != null && !bookId.isEmpty()) {
            BookDto bookDtoForModify = service.findById(bookId);
            bookDtoForModify.setName(bookDto.getName());
            book = BookDto.toDomainObject(bookDtoForModify);
        }
        service.save(book);
        return "redirect:/";
    }

    @PostMapping("/deleteBook")
    public String deleteById(@RequestParam(value = "id", required = true) String bookId) {
        service.deleteById(bookId);
        return "redirect:/";
    }

    @PostMapping("/delete")
    public String deleteAll() {
        service.deleteAll();
        return "redirect:/";
    }

    @RequestMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("OK");
    }

    @RequestMapping("/badRequest")
    public ResponseEntity error() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @ExceptionHandler(EntityNotFound.class)
    public ResponseEntity<String> handleNotEnoughFunds(EntityNotFound ex) {
        return ResponseEntity.badRequest().body("Not found");
    }
}
