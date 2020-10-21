package ru.otus.library.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import reactor.core.publisher.Flux;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;
import ru.otus.library.repository.BookRepository;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ChangeLog(order = "001")
public class MongoDBInit {
    @ChangeSet(order = "001", id = "clear", runAlways = true, author = "abashmakov")
    public void dropDB(MongoDatabase database){
        database.drop();
    }

    @ChangeSet(order = "002", id = "init", runAlways = true, author = "abashmakov")
    public void initDB(BookRepository bookRepository){
        Book book1 = initBook("Famous Book", new String[] {"Talent", "Pushkin"}, new String[] {"Lyrics", "Roman"}, new String[]{});
        Book book2 = initBook("Incredible Book", new String[] {"Bob", "Sam", "Joe"}, new String[] {"Fantasy", "Detective"}, new String[]{});
        bookRepository.insert(Flux.just(book1, book2)).subscribe();
    }

    private Book initBook(String title, String[] authors, String[] genres, String[] comments) {
        List<Author> authorList = Arrays.stream(authors)
                .map(author -> Author.builder()
                        .id(UUID.randomUUID().toString())
                        .name(author)
                        .build()).collect(Collectors.toList());
        List<Genre> genreList = Arrays.stream(genres)
                .map(genre -> Genre.builder()
                        .id(UUID.randomUUID().toString())
                        .name(genre)
                        .build()).collect(Collectors.toList());
        return Book.builder()
                .name(title)
                .authors(authorList)
                .genres(genreList)
                .build();
    }
}
