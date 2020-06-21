package ru.otus.spring.dao;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import ru.otus.spring.domain.Question;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class QuestionDaoImpl implements QuestionDao {

    private final String path;

    @Override
    public List<Question> getQuestions() {
        List<Question> questions ;
        try {
            InputStream inputStream = this.getClass().getResourceAsStream(path);
            CSVParser parser = new CSVParser(new InputStreamReader(inputStream), CSVFormat.EXCEL.withDelimiter(';'));
            questions = parser.getRecords().stream().map(this::parseQuestion).collect(Collectors.toList());
        } catch (IOException e) {
            return Collections.emptyList();
        }

        return questions;
    }

    private Question parseQuestion(CSVRecord record) {
        String question = record.size() > 0 ? record.get(0) : "";
        String correctAnswer = record.size() > 1 ? record.get(1) : "";
        List<String> answers = new ArrayList<>();
        if (record.size() > 1) {
            for (int i = 1; i < record.size(); i++) {
                answers.add(record.get(i));
            }
        }
        //чтобы не повторялся порядок вывода
        Collections.shuffle(answers);
        return new Question(question, correctAnswer, answers);

    }
}
