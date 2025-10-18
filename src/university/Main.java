package university;
//Вариант 13
//Разработайте и реализуйте объектно-ориентированную модель, на основе которой можно будет создать автоматизированную систему регистрации
// и сопровождения индивидуальных траекторий обучения. Индивидуальная траектория обучения складывается из:
//        - обязательных для всех дисциплин;
//- рекомендуемых факультативных дисциплин, из которых учащимся может быть выбрано не менее n дисциплин.
//В рамках обязательных и факультативных дисциплин могут выполняться учебные проекты, которые представляют собой множество задач,
// причем каждая задача может быть зарегистрирована в качестве альтернативного варианта лабораторной или практической работы.

import university.controller.AppController;
import university.model.LearningTrajectory;
import university.model.Student;
import university.model.entities.*;
import university.view.MainFrame;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        LearningTrajectory trajectory = createDemoTrajectory();
        Student student = new Student("Иван Петров");

        AppController controller = new AppController(trajectory);

        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame(controller, trajectory, student);
            controller.setMainFrame(frame); // Даем контроллеру ссылку на фрейм
            frame.setVisible(true);
            controller.initialDisplay(); // Первоначальная отрисовка
        });
    }

    private static LearningTrajectory createDemoTrajectory() {
        // Обязательные дисциплины
        List<MandatoryDiscipline> mandatory = Arrays.asList(
                new MandatoryDiscipline("Математический анализ",
                        new EducationalProject("Исследование рядов", new ArrayList<>())
                                .addTask(new Task("Изучение сходимости"))
                ),
                new MandatoryDiscipline("Основы программирования",
                        new EducationalProject("Разработка калькулятора", new ArrayList<>())
                                .addTask(new Task("Реализация базовых операций (+, -, *, /)"))
                )
        );

        // Факультативные дисциплины
        List<ElectiveDiscipline> electives = new ArrayList<>(Arrays.asList(
                new ElectiveDiscipline("Машинное обучение",
                        new EducationalProject("Классификатор изображений", new ArrayList<>())
                                .addTask(new Task("Сбор и разметка датасета"))
                                .addTask(new Task("Обучение модели сверточной сети"))
                                .addTask(new Task("Оценка точности на тестовой выборке"))
                ),
                new ElectiveDiscipline("Веб-разработка",
                        new EducationalProject("Создание блога", new ArrayList<>())
                                .addTask(new Task("Проектирование БД для постов и комментариев"))
                ),
                new ElectiveDiscipline("Теория графов",
                        new EducationalProject("Алгоритм Дейкстры", new ArrayList<>())
                ),
                new ElectiveDiscipline("Компьютерная графика",
                        new EducationalProject("3D-модель", new ArrayList<>())
                ),
                new ElectiveDiscipline("ШППО",
                        new EducationalProject("Паттерны", new ArrayList<>())
                                .addTask(new Task("Реализовать паттерн MVC"))
                                .addTask(new Task("Реализовать паттерн Наблюдатель"))
                ),
                new ElectiveDiscipline("МСКЗИ",
                        new EducationalProject("Криптографические основы", new ArrayList<>())
                )
        ));

        return new LearningTrajectory(mandatory, electives);
    }
}