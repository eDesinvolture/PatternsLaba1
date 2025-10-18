package university.view.patterns.pool;

import university.model.entities.Discipline;
import university.model.entities.ElectiveDiscipline;
import university.model.entities.Task;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.function.Consumer;

public class DisciplineWidget extends JPanel {
    private final JLabel nameLabel = new JLabel();
    private final JButton deleteButton = new JButton("Удалить");
    private final JPanel detailsPanel = new JPanel();
    private final JLabel projectNameLabel = new JLabel();
    private final JPanel tasksPanel = new JPanel(); // Панель для списка задач

    private Discipline currentDiscipline;
    private Consumer<Discipline> deleteAction;

    public DisciplineWidget() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                new EmptyBorder(5, 5, 5, 5))
        );
        setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.add(new JLabel("Курс:"));
        headerPanel.add(nameLabel);
        headerPanel.add(deleteButton);
        headerPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setBorder(BorderFactory.createTitledBorder("Учебный проект"));
        detailsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        projectNameLabel.setBorder(new EmptyBorder(0, 5, 5, 0));
        tasksPanel.setLayout(new BoxLayout(tasksPanel, BoxLayout.Y_AXIS));
        tasksPanel.setBorder(new EmptyBorder(0, 15, 0, 0));

        detailsPanel.add(projectNameLabel);
        detailsPanel.add(tasksPanel);

        add(headerPanel);
        add(detailsPanel);

        deleteButton.addActionListener(e -> {
            if (deleteAction != null && currentDiscipline != null) {
                deleteAction.accept(currentDiscipline);
            }
        });
    }

    public void setData(Discipline discipline, Consumer<Discipline> deleteAction, boolean showDetails) {
        this.currentDiscipline = discipline;
        this.nameLabel.setText("<html><b>" + discipline.name + "</b></html>");
        this.deleteAction = deleteAction;

        // Логика видимости кнопки удаления (осталась прежней)
        deleteButton.setVisible(deleteAction != null);

        if (showDetails && discipline.project != null) {
            projectNameLabel.setText("Название: " + discipline.project.name);

            // Очищаем панель от старых задач перед добавлением новых
            tasksPanel.removeAll();
            if (discipline.project.tasks.isEmpty()) {
                tasksPanel.add(new JLabel("<html><i>Задачи не определены</i></html>"));
            } else {
                for (Task task : discipline.project.tasks) {
                    tasksPanel.add(new JLabel("• " + task.name));
                }
            }
            detailsPanel.setVisible(true);
        } else {
            detailsPanel.setVisible(false);
        }
    }
}