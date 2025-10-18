package university.view.patterns.display.strategy;

import university.controller.AppController; // <-- Импорт все равно нужен
import university.model.LearningTrajectory;
import university.view.patterns.pool.DisciplineWidget;
import university.view.patterns.pool.WidgetPool;

import javax.swing.*;
import java.awt.*;

public class SpiralDisplayStrategy implements IDisplayStrategy {

    @Override
    public void draw(JPanel panel, LearningTrajectory trajectory, AppController controller) {
        panel.setLayout(new BorderLayout());

        HypercubeInteractivePanel hypercubePanel = new HypercubeInteractivePanel();
        panel.add(hypercubePanel, BorderLayout.CENTER);

        // Создаем панель управления.
        JPanel controlsPanel = new JPanel(new GridLayout(1, 1, 10, 0));
        controlsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JSlider speedSlider = new JSlider(0, 100, 20);
        speedSlider.setBorder(BorderFactory.createTitledBorder("Скорость вращения"));
        speedSlider.addChangeListener(e -> {
            hypercubePanel.setRotationSpeed(speedSlider.getValue() / 1000.0);
        });

        // Устанавливаем начальную скорость.
        hypercubePanel.setRotationSpeed(speedSlider.getValue() / 1000.0);

        controlsPanel.add(speedSlider);
        panel.add(controlsPanel, BorderLayout.SOUTH);

        // 2. Наполняем гиперкуб данными (эта часть остается без изменений).
        // Она будет работать с локальной переменной hypercubePanel, которую мы только что создали.
        hypercubePanel.clearWidgets();
        WidgetPool pool = WidgetPool.getInstance();
        int faceIndex = 0;

        // Размещаем обязательные дисциплины.
        for (var discipline : trajectory.getMandatoryDisciplines()) {
            if (faceIndex >= 8) break;
            DisciplineWidget widget = pool.getWidget();
            widget.setData(discipline, null, false);
            hypercubePanel.setWidgetForFace(faceIndex++, widget);
        }

        // Размещаем выбранные факультативы.
        for (var discipline : trajectory.getChosenElectives()) {
            if (faceIndex >= 8) break;
            DisciplineWidget widget = pool.getWidget();
            widget.setData(discipline, null, false);
            hypercubePanel.setWidgetForFace(faceIndex++, widget);
        }
    }
}