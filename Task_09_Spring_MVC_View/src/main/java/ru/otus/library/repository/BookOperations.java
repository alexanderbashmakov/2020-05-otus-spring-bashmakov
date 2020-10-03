package ru.otus.library.repository;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Identable;
import ru.otus.library.dto.CountDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class BookOperations {
    private final MongoTemplate mongoTemplate;

    public <T extends Identable> void createElement(@NonNull String bookId, T element, String array) {
        element.setId(UUID.randomUUID().toString());
        Update update = new Update().push(array).value(element);
        mongoTemplate.updateMulti(new Query(Criteria.where("_id").is(bookId)), update, Book.class);
    }

    public <T extends Identable> void updateElement(@NonNull String id, T element, String array) {
        element.setId(id);
        Update update = new Update()
                .filterArray("element._id", id)
                .set(array + ".$[element]", element);
        mongoTemplate.updateMulti(new Query(), update, Book.class);
    }

    public <T> Page<T> findElements(Pageable pageable, String bookId, Class<T> dtoClass, String array) {
        UnwindOperation unwind = Aggregation.unwind(array);
        AddFieldsOperation addFields = Aggregation.addFields()
                .addFieldWithValue(array + ".bookId", "$_id")
                .addFieldWithValue(array + ".bookName", "$name")
                .build();
        ReplaceRootOperation replaceRoot = Aggregation.replaceRoot(array);
        MatchOperation match = bookId != null ? new MatchOperation(Criteria.where("_id").is(bookId)) : null;
        SkipOperation skip = Aggregation.skip((long) pageable.getPageNumber() * pageable.getPageSize());
        LimitOperation limit = Aggregation.limit(pageable.getPageSize());
        CountOperation count = Aggregation.count().as("total");

        Aggregation aggregationCount = bookId == null ? Aggregation.newAggregation(unwind, count) : Aggregation.newAggregation(match, unwind, count);
        Aggregation aggregation = bookId == null ?
                Aggregation.newAggregation(unwind, addFields, replaceRoot, skip, limit) :
                Aggregation.newAggregation(match, unwind, addFields, replaceRoot, skip, limit);

        CountDto countDto = Optional.ofNullable(mongoTemplate.aggregate(aggregationCount, Book.class, CountDto.class).getUniqueMappedResult()).orElse(new CountDto(0L));
        List<T> result = mongoTemplate.aggregate(aggregation, Book.class, dtoClass).getMappedResults();
        return new PageImpl<>(result, pageable, countDto.getTotal());
    }

    public void deleteById(String id, String array) {
        Update update = new Update().pull(array, Query.query(Criteria.where("_id").is(id)));
        mongoTemplate.updateMulti(new Query(), update, Book.class);
    }

    public void deleteAll(String array) {
        Update update = new Update().set(array, new ArrayList<>());
        mongoTemplate.updateMulti(new Query(), update, Book.class);
    }
}
