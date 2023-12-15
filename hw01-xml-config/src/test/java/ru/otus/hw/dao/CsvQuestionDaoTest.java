package ru.otus.hw.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.service.TestRunnerService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"/test-context.xml"})
class CsvQuestionDaoTest {

    private CsvQuestionDao dao;
    private List<Question> questions;
    Question question1;
    Question question2;

    @BeforeEach
    void setUp() {
        ApplicationContext context = new ClassPathXmlApplicationContext("test-context.xml");
        dao = context.getBean("questionDao", CsvQuestionDao.class);
        question1 = new Question("What is the most common color of toilet paper in France?", List.of(new Answer("Pink", true),
                new Answer("White", false), new Answer("Blue", false)));
        question2 = new Question("What color is an airplane’s famous black box?", List.of(new Answer("Red", false),
                new Answer("Orange", true), new Answer("Black", false)));
        questions = List.of(question1, question2);
    }

    @Test
    void findAll() {
        final List<Question> actual = dao.findAll();

        assertThat(actual).isNotNull();
        assertThat(actual).hasSize(2)
                .contains(question1, question2);
    }
}