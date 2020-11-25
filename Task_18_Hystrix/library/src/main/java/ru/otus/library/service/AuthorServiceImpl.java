package ru.otus.library.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.domain.Author;
import ru.otus.library.dto.AuthorDto;
import ru.otus.library.repository.AuthorRepository;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorDto reserveAuthor = AuthorDto.builder().name("N/A").build();

    @Transactional
    @Override
    @HystrixCommand(commandKey = "authors", fallbackMethod = "fallbackSave")
    public void create(@NonNull String bookId, Author author) {
        authorRepository.create(bookId, author);
    }

    @Transactional
    @Override
    @HystrixCommand(commandKey = "authors", fallbackMethod = "fallbackSave")
    public void update(@NonNull String id, Author author) {
        authorRepository.update(id, author);
    }

    public void fallbackSave(String id, Author author) {
        log.error("Unable to create or update author");
    }

    @Transactional
    @Override
    @HystrixCommand(commandKey = "authors", fallbackMethod = "fallbackFindAllByBook")
    public List<AuthorDto> findAllByBook(String bookId) {
        sleepRandomly();
        return authorRepository.findAuthors(bookId);
    }

    public List<AuthorDto> fallbackFindAllByBook(String bookId) {
        return List.of(reserveAuthor);
    }

    @Transactional
    @Override
    @HystrixCommand(commandKey = "authors", fallbackMethod = "fallbackFindById")
    public Optional<AuthorDto> findById(String id) {
        return authorRepository.findById(id);
    }

    public Optional<AuthorDto> fallbackFindById(String id) {
        return Optional.of(reserveAuthor);
    }

    @Transactional
    @Override
    @HystrixCommand(commandKey = "authors", fallbackMethod = "fallbackDeleteById")
    public void deleteById(String id) {
        authorRepository.deleteById(id);
    }

    public void fallbackDeleteById(String id) {log.error(String.format("Unable to delete author with id = %s", id));}

    @Transactional
    @Override
    @HystrixCommand(commandKey = "authors", fallbackMethod = "fallbackDeleteAll")
    public void deleteAll() {
        authorRepository.deleteAll();
    }

    public void fallbackDeleteAll() {log.error("Unable to delete authors");}

    private void sleepRandomly() {
        Random rand = new Random();
        int randomNum = rand.nextInt(3) + 1;
        if(randomNum == 3) {
            try {
                log.info("Start sleeping...." + System.currentTimeMillis());
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                log.warn("Hystrix thread interupted...." + System.currentTimeMillis());
            }
        }
    }
}
