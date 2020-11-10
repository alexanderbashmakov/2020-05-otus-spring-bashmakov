package ru.otus.springbatch.chandgelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import com.mongodb.client.MongoDatabase;
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
        template.insert(initBook("Famous Book", new String[] {"Lyrics", "Roman"}));
        template.insert(initBook("Incredible Book", new String[] {"Fantasy", "Detective", "Lyrics"}));
        template.insert(initBook("Kinder Book", new String[] {"Fantasy"}));
        template.insert(initBook("Cooking Book", new String[] {"Cooking"}));
        template.insert(initBook("Dictionary", new String[] {"Sciense", "Language"}));
        template.insert(initBook("True story", new String[] {"History", "Adventure"}));
    }

    private Book initBook(String title, String[] genres) {
        List<Genre> genreList = Arrays.stream(genres)
                .map(genre -> Genre.builder()
                        .id(UUID.randomUUID().toString())
                        .name(genre)
                        .build()).collect(Collectors.toList());
        return Book.builder()
                .name(title)
                .genres(genreList)
                .build();
    }
}
