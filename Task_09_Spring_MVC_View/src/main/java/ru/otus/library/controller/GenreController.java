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
import ru.otus.library.domain.Genre;
import ru.otus.library.dto.GenreDto;
import ru.otus.library.exceptions.EntityNotFound;
import ru.otus.library.service.GenreService;

@Controller
@RequiredArgsConstructor
public class GenreController {

    private final GenreService service;

    @GetMapping(value = "/genres")
    public ModelAndView authorList(Model model,
                                   @RequestParam(value = "bookId", required = false) String bookId,
                                   @RequestParam(value = "page", defaultValue = "0") Integer curPage,
                                   @RequestParam(value = "size", defaultValue = "5") Integer size) {
        ModelAndView mvc = new ModelAndView("genreList");
        Page<GenreDto> page = service.findAllByBook(PageRequest.of(curPage, size), bookId);
        mvc.addObject("bookId", bookId);
        PageUtils.addPageParams(mvc, page);
        return mvc;
    }

    @GetMapping(value = "/genre")
    public ModelAndView getAuthor(@RequestParam(value = "bookId", required = false) String bookId, @RequestParam(value = "id", required = false) String id) {
        ModelAndView mvc = new ModelAndView("genre");

        if (id != null && !id.isEmpty()) {
            mvc.addObject("genre", service.findById(id).orElseThrow(EntityNotFound::new));
        } else {
            mvc.addObject("genre", new GenreDto());
        }
        mvc.addObject("bookId", bookId);
        return mvc;
    }

    @PostMapping(value = "/genre")
    public String editAuthor(@RequestParam(value = "bookId") String bookId, @RequestParam(value = "id", required = false) String id, GenreDto genreDto) {
        Genre genre = GenreDto.toDomainObject(genreDto);
        if (id.isEmpty()) {
            service.create(genreDto.getBookId(), genre);
        } else {
            service.update(id, genre);
        }
        return String.format("redirect:/genres?bookId=%s", bookId);
    }

    @PostMapping("/genre-delete")
    public String deleteById(@RequestParam(value = "bookId") String bookId, @RequestParam(value = "id", required = true) String id) {
        service.deleteById(id);
        return String.format("redirect:/genres?bookId=%s", bookId);
    }

}
