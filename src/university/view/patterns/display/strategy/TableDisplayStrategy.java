package university.view.patterns.display.strategy;

import university.controller.AppController;
import university.model.LearningTrajectory;
import university.model.entities.ElectiveDiscipline;
import university.view.patterns.pool.DisciplineWidget;
import university.view.patterns.pool.WidgetPool;
import javax.swing.*;

public class TableDisplayStrategy implements IDisplayStrategy {
    @Override
    public void draw(JPanel panel, LearningTrajectory trajectory, AppController controller) {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        WidgetPool pool = WidgetPool.getInstance();

        panel.add(new JLabel("<html><h3>Обязательные дисциплины</h3></html>"));
        trajectory.getMandatoryDisciplines().forEach(d -> {
            DisciplineWidget widget = pool.getWidget();
            widget.setData(d, null, true);
            panel.add(widget);
        });

        panel.add(Box.createVerticalStrut(15));
        panel.add(new JLabel("<html><h3>Выбранные факультативы</h3></html>"));
        trajectory.getChosenElectives().forEach(d -> {
            DisciplineWidget widget = pool.getWidget();
            widget.setData(
                    d,
                    (discipline) -> controller.removeElective((ElectiveDiscipline) discipline),
                    true
            );
            panel.add(widget);
        });
    }
}