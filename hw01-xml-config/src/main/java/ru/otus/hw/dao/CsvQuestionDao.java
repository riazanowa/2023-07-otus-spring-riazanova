package ru.otus.hw.dao;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.dao.dto.QuestionDto;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CsvQuestionDao implements QuestionDao {
    private final TestFileNameProvider fileNameProvider;

    @Override
    public List<Question> findAll() {
        BufferedReader reader = null;
        try {
            reader = fetchFileFromResourceAsStream(fileNameProvider.getTestFileName());
            final List<QuestionDto> questionsDto = new CsvToBeanBuilder<QuestionDto>(reader)
                    .withSkipLines(1)
                    .withType(QuestionDto.class)
                    .withSeparator(';')
                    .build()
                    .parse();

            return questionsDto.stream().map(QuestionDto::toDomainObject).toList();
        } catch (IOException e) {
            throw new QuestionReadException("Error while reading csv file: ", e);
        }
    }

    private BufferedReader fetchFileFromResourceAsStream(String fileName) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        final String testFileName = fileNameProvider.getTestFileName();
        InputStream inputStream = classLoader.getResourceAsStream(testFileName);

        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        }
    }
}

