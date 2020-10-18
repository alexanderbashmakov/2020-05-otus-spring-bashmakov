package ru.otus.library.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.otus.library.dto.GenreDto;
import ru.otus.library.exceptions.EntityNotFound;
import ru.otus.library.service.GenreService;

@Controller
@RequiredArgsConstructor
public class GenreController {

    private final GenreService service;

    @GetMapping(value = "/genres")
    public String genreList(Model model, @RequestParam(value = "bookId", required = false) String bookId) {
        model.addAttribute("bookId", bookId);
        return "genreList";
    }

    @GetMapping(value = "/genre")
    public ModelAndView getGenre(@RequestParam(value = "bookId", required = false) String bookId, @RequestParam(value = "id", required = false) String id) {
        ModelAndView mvc = new ModelAndView("genre");

        if (id != null && !id.isEmpty()) {
            mvc.addObject("genre", service.findById(id).orElseThrow(EntityNotFound::new));
        } else {
            mvc.addObject("genre", new GenreDto());
        }
        mvc.addObject("bookId", bookId);
        return mvc;
    }
}
