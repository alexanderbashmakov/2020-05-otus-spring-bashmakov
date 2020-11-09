package ru.otus.springbatch.chandgelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import com.mongodb.client.MongoDatabase;
import ru.otus.springbatch.model.Author;
import ru.otus.springbatch.model.Book;
import ru.otus.springbatch.model.Genre;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ChangeLog(order = "001")
public class InitMongoDBDataChangeLog {
    @ChangeSet(order = "001", id = "clear", runAlways = true, author = "abashmakov")
    public void dropDB(MongoDatabase database){
        database.drop();
    }

    @ChangeSet(order = "002", id = "init", runAlways = true, author = "abashmakov")
    public void initDB(MongockTemplate template){
        template.insert(initBook("Famous Book", new String[] {"Talent", "Pushkin"}, new String[] {"Lyrics", "Roman"}, new String[]{}));
        template.insert(initBook("Incredible Book", new String[] {"Bob", "Sam", "Joe"}, new String[] {"Fantasy", "Detective", "Lyrics"}, new String[]{}));
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
