package ru.otus.hw.shell;


import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import ru.otus.hw.service.LocalizedIOService;
import ru.otus.hw.service.StudentService;
import ru.otus.hw.service.TestRunnerService;

@ShellComponent
@RequiredArgsConstructor
public class ApplicationShellCommandsRunner {

    private final LocalizedIOService ioService;

    private final TestRunnerService testRunnerService;

    private final StudentService studentService;

    private String firstName;
    private String lastName;

    @ShellMethod(value = "Login command", key = {"l", "login"})
    public String login(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        studentService.determineCurrentStudent(firstName, lastName);
        return ioService.getMessage("ApplicationShellCommandsRunner.welcome.message", firstName, lastName);
    }

    @ShellMethod(value = "Execute test", key = {"start", "test"})
    @ShellMethodAvailability(value = "")
    public void executeTest() {
        testRunnerService.run();
    }

    private Availability isNameAvailable() {
        return firstName == null || lastName == null ?
                Availability.unavailable(ioService.getMessage("ApplicationShellCommandsRunner.reason"))
                : Availability.available();
    }
}
