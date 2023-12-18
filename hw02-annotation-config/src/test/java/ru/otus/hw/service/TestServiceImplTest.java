package ru.otus.hw.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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

class TestServiceImplTest {
    private TestServiceImpl testService;
    @Mock
    private QuestionDao questionDao = mock(QuestionDao.class);
    @Mock
    private IOService ioService = mock(IOService.class);

    private Student testStudent = new Student("firstName", "lastName");

    @BeforeEach
    void setUp() {
        testService = new TestServiceImpl(ioService, questionDao);
        List<Question> questions = List.of(new Question("What is the most common color of toilet paper in France?",
                        List.of(new Answer("Pink", true), new Answer("White", false), new Answer("Blue", false))),
                new Question("What color is an airplane’s famous black box?", List.of(new Answer("Red", false),
                        new Answer("Orange", true), new Answer("Black", false))));
        when(questionDao.findAll()).thenReturn(questions);
        when(ioService.readIntForRangeWithPrompt(anyInt(), anyInt(), anyString(), anyString())).thenReturn(1);
    }

    @Test
    void executeTestFor() {
        final TestResult actual = testService.executeTestFor(testStudent);

        assertEquals(1, actual.getRightAnswersCount());
    }
}