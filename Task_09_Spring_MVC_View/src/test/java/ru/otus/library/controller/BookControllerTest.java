package ru.otus.library.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.otus.library.domain.Book;
import ru.otus.library.dto.BookDto;
import ru.otus.library.service.BookService;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Класс BookController:")
@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {

    @Autowired
    private BookController bookController;
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService service;

    @Test
    @DisplayName("/ method GET")
    public void bookList() throws Exception{
        final String bookId = "1";
        final int page = 0;
        final int size = 5;
        final BookDto bookDto = BookDto.builder()
                .id(bookId)
                .name("testBook")
                .build();
        PageRequest pageRequest = PageRequest.of(page, size);
        when(service.findAll(pageRequest)).thenReturn(new PageImpl<>(List.of(bookDto), pageRequest, 1));
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("bookList"))
                .andExpect(content().string(containsString(bookDto.getName())))
                .andExpect(content().string(containsString(bookId)));
        verify(service).findAll(pageRequest);
    }

    @Test
    @DisplayName("/book method GET")
    public void get() throws Exception{
        final String bookId = "1";
        final Book book = Book.builder()
                .id(bookId)
                .name("testBook")
                .authors(new ArrayList<>())
                .genres(new ArrayList<>())
                .build();
        when(service.findById(bookId)).thenReturn(BookDto.toDto(book));
        mockMvc.perform(MockMvcRequestBuilders.get(String.format("/book?id=%s", bookId)))
                .andExpect(status().isOk())
                .andExpect(view().name("book"))
                .andExpect(content().string(containsString(book.getName())));
        verify(service).findById(bookId);
    }

    @Test
    @DisplayName("/edit method POST")
    public void post() throws Exception{
        final String bookId = "1";
        final Book book = Book.builder()
                .id(bookId)
                .name("testBook")
                .authors(new ArrayList<>())
                .genres(new ArrayList<>())
                .build();
        when(service.findById(bookId)).thenReturn(BookDto.toDto(book));
        mockMvc.perform(MockMvcRequestBuilders.post("/edit").param("id", bookId).flashAttr("bookDto", BookDto.toDto(book)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
        verify(service).save(book);
    }
}