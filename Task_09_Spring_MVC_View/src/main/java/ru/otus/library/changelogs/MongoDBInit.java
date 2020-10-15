package ru.otus.library.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.domain.Genre;
import ru.otus.library.repository.BookRepository;

import java.util.Arrays;
import java.util.Date;
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
        bookRepository.insert(initBook("Famous Book", new String[] {"Talent", "Pushkin"}, new String[] {"Lyrics", "Roman"}, new String[]{}));
        bookRepository.insert(initBook("Incredible Book", new String[] {"Bob", "Sam", "Joe"}, new String[] {"Fantasy", "Detective"}, new String[]{}));
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
        List<Comment> commentList = Arrays.stream(comments)
                .map(comment -> Comment.builder()
                        .id(UUID.randomUUID().toString())
                        .created(new Date())
                        .comment(comment)
                        .build()).collect(Collectors.toList());
        return Book.builder()
                .name(title)
                .authors(authorList)
                .genres(genreList)
                .comments(commentList)
                .build();
    }
}
