package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.domain.Student;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private Student student;

    @Override
    public void determineCurrentStudent(String firstName, String lastName) {
        student = new Student(firstName, lastName);
    }

    @Override
    public Student getCurrentStudent() {
        return student;
    }
}
