package ru.otus.springbatch.reader;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.data.AbstractPaginatedDataItemReader;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.Fields;
import org.springframework.util.Assert;
import ru.otus.springbatch.model.Book;

import java.util.Iterator;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@RequiredArgsConstructor
public class AggregateMongoReader<T> extends AbstractPaginatedDataItemReader<T> implements InitializingBean {

    private final MongoTemplate mongoTemplate;
    private final String collection;
    private final Class<? extends T> targetClass;

    @SuppressWarnings("unchecked")
    @Override
    protected Iterator<T> doPageRead() {
        Aggregation aggregation = newAggregation(unwind(collection),
                replaceRoot(collection),
                group(Fields.fields("$name")),
                addFields().addFieldWithValue("name","$_id").build(),
                skip((long) pageSize * page),
                limit(pageSize));
        return (Iterator<T>) mongoTemplate.aggregate(aggregation, Book.class, targetClass).iterator();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.state(mongoTemplate != null, "An implementation of MongoOperations is required.");
        Assert.state(collection != null, "A nested collection name is required.");
        Assert.state(targetClass != null, "A type to convert the input into is required.");
    }


}
