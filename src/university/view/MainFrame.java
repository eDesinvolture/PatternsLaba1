package university.view;

import university.controller.AppController;
import university.model.LearningTrajectory;
import university.model.entities.ElectiveDiscipline;
import university.model.Student;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private final AppController controller;
    private final LearningTrajectory trajectory;
    private final JLabel statusLabel = new JLabel();
    private JComboBox<ElectiveDiscipline> electiveComboBox;
    private final Student student;

    public MainFrame(AppController controller, LearningTrajectory trajectory, Student student) {
        this.controller = controller;
        this.trajectory = trajectory;
        this.student = student;


        setTitle("Образовательная траектория студента: " + this.student.getName());
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Панель для отображения (ее мы и будем адаптировать)
        JPanel mainDisplayPanel = new JPanel();
        JScrollPane scrollPane = new JScrollPane(mainDisplayPanel);
        add(scrollPane, BorderLayout.CENTER);

        // Передаем панель в адаптер
        controller.setView(mainDisplayPanel);

        add(createControlPanel(), BorderLayout.SOUTH);
        add(createHeaderPanel(), BorderLayout.NORTH);

        updateStatus();
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.add(statusLabel);
        return headerPanel;
    }

    private JPanel createControlPanel() {
        JPanel controlPanel = new JPanel();

        // Бокс для выбора факультатива
        ElectiveDiscipline[] availableElectives = trajectory.getAvailableElectives().toArray(new ElectiveDiscipline[0]);
        this.electiveComboBox = new JComboBox<>(availableElectives);
        controlPanel.add(electiveComboBox);

        // Кнопка для записи на курс
        JButton chooseButton = new JButton("Выбрать курс");
        chooseButton.addActionListener(e -> {
            ElectiveDiscipline selected = (ElectiveDiscipline) electiveComboBox.getSelectedItem();
            if (selected != null) {
                // Больше не нужен Object, передаем сразу нужный тип.
                controller.selectElective(selected);
            }
        });
        controlPanel.add(chooseButton);

        // Кнопка для смены вида (стратегии/состояния)
        JButton toggleViewButton = new JButton("Сменить вид");
        toggleViewButton.addActionListener(e -> controller.toggleDisplayState());
        controlPanel.add(toggleViewButton);

        return controlPanel;
    }

    public void updateStatus() {
        // Обновление статусной строки
        int chosen = trajectory.getChosenElectives().size();
        statusLabel.setText(String.format("Минимальное кол-во факультативов 2. Выбрано факультативов: %d", chosen));


        // Обновляем модель ComboBox
        DefaultComboBoxModel<ElectiveDiscipline> model = (DefaultComboBoxModel<ElectiveDiscipline>) electiveComboBox.getModel();
        model.removeAllElements();
        model.addAll(trajectory.getAvailableElectives());

    }
}