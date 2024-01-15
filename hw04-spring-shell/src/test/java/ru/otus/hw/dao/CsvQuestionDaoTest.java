package ru.otus.hw.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = CsvQuestionDao.class)
class CsvQuestionDaoTest {
    @Autowired
    private CsvQuestionDao dao;
    @MockBean
    private TestFileNameProvider fileNameProvider;
    Question question1;
    Question question2;

    @BeforeEach
    void setUp() {
        when(fileNameProvider.getTestFileName()).thenReturn("test-questions.csv");
        question1 = new Question("What is the most common color of toilet paper in France?", List.of(new Answer("Pink", true),
                new Answer("White", false), new Answer("Blue", false)));
        question2 = new Question("What color is an airplane’s famous black box?", List.of(new Answer("Red", false),
                new Answer("Orange", true), new Answer("Black", false)));
    }

    @Test
    void testFindAll() {
        final List<Question> actual = dao.findAll();

        assertThat(actual).isNotNull();
        assertThat(actual).hasSize(2)
                .contains(question1, question2);
    }
}