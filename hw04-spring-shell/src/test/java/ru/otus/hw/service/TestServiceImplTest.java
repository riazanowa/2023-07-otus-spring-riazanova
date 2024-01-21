package ru.otus.hw.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.hw.dao.CsvQuestionDao;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
@SpringBootTest(classes = TestServiceImpl.class)
class TestServiceImplTest {
    @Autowired
    private TestServiceImpl testService;
    @MockBean
    private QuestionDao questionDao;
    @MockBean
    private LocalizedIOService ioService;

    private final Student testStudent = new Student("firstName", "lastName");

    @BeforeEach
    void setUp() {
        List<Question> questions = List.of(new Question("What is the most common color of toilet paper in France?",
                        List.of(new Answer("Pink", true), new Answer("White", false), new Answer("Blue", false))),
                new Question("What color is an airplane’s famous black box?", List.of(new Answer("Red", false),
                        new Answer("Orange", true), new Answer("Black", false))));
        when(questionDao.findAll()).thenReturn(questions);
        when(ioService.readIntForRangeWithPromptLocalized(anyInt(), anyInt(), anyString(), anyString())).thenReturn(1);
    }

    @Test
    void executeTestFor() {
        final TestResult actual = testService.executeTestFor(testStudent);

        assertEquals(1, actual.getRightAnswersCount());
    }
}