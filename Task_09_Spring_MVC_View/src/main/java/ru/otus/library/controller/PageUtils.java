package ru.otus.library.controller;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@UtilityClass
public class PageUtils {
    void addPageParams(ModelAndView modelAndView, Page<?> page) {
        modelAndView.addObject("page", page);

        int totalPages = page.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            modelAndView.addObject("pageNumbers", pageNumbers);
        }
    }
}
