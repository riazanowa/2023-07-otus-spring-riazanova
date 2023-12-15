package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Question;

import java.util.List;

@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private final QuestionDao questionDao;

    @Override
    public void executeTest() {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
        // Получить вопросы из дао и вывести их с вариантами ответов
        final List<Question> questions = questionDao.findAll();
        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            ioService.printFormattedLine("%d." + question.text(), i+1);
            for (int j = 0; j < question.answers().size(); j++) {
                ioService.printFormattedLine("%d. %s - %b", 1 + j, question.answers().get(j).text(),
                        question.answers().get(j).isCorrect());
            }
        }
    }
}
