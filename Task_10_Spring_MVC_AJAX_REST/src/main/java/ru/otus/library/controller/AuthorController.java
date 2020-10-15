package ru.otus.library.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.otus.library.domain.Author;
import ru.otus.library.dto.AuthorDto;
import ru.otus.library.exceptions.EntityNotFound;
import ru.otus.library.service.AuthorService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService service;

    @GetMapping(value = "/authors")
    public String authorList(Model model, @RequestParam(value = "bookId", required = false) String bookId) {
        List<AuthorDto> authors = service.findAllByBook(bookId);
        model.addAttribute("bookId", bookId);
        model.addAttribute("authors", authors);
        return "authorList";
    }

    @GetMapping(value = "/author")
    public ModelAndView getAuthor(@RequestParam(value = "bookId", required = false) String bookId, @RequestParam(value = "id", required = false) String id) {
        ModelAndView mvc = new ModelAndView("author");

        if (id != null && !id.isEmpty()) {
            mvc.addObject("author", service.findById(id).orElseThrow(EntityNotFound::new));
        } else {
            mvc.addObject("author", new AuthorDto());
        }
        mvc.addObject("bookId", bookId);
        return mvc;
    }

    @PostMapping(value = "/author")
    public String editAuthor(@RequestParam(value = "bookId") String bookId, @RequestParam(value = "id", required = false) String id, AuthorDto authorDto) {
        Author author = AuthorDto.toDomainObject(authorDto);
        if (id.isEmpty()) {
            service.create(authorDto.getBookId(), author);
        } else {
            service.update(id, author);
        }
        return String.format("redirect:/authors?bookId=%s", bookId);
    }

    @PostMapping("/author-delete")
    public String deleteById(@RequestParam(value = "bookId") String bookId, @RequestParam(value = "id", required = true) String id) {
        service.deleteById(id);
        return String.format("redirect:/authors?bookId=%s", bookId);
    }

}
