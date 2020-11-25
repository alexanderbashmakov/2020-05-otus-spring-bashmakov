package ru.otus.library.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.otus.library.dto.AuthorDto;
import ru.otus.library.exceptions.EntityNotFound;
import ru.otus.library.service.AuthorService;

@Controller
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService service;

    @GetMapping(value = "/authors")
    public String authorList(Model model, @RequestParam(value = "bookId", required = false) String bookId) {
        model.addAttribute("bookId", bookId);
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
}
