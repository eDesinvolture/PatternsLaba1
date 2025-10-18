package university.view.patterns;

import university.controller.AppController;
import university.model.LearningTrajectory;
import university.view.patterns.display.state.IDisplayState;
import university.view.patterns.display.strategy.IDisplayStrategy;
import university.view.patterns.pool.WidgetPool;

import javax.swing.*;

public class MainPanelAdapter implements IView {

    private final JPanel panel;
    private IDisplayStrategy currentStrategy;
    private LearningTrajectory trajectory;
    private AppController controller;

    public MainPanelAdapter(JPanel panel) {
        this.panel = panel;
    }

    @Override
    public void displayTrajectory(LearningTrajectory trajectory, AppController controller) {
        this.trajectory = trajectory;
        this.controller = controller;
        if (currentStrategy != null) {
            clearPanel();
            currentStrategy.draw(this.panel, this.trajectory, this.controller);
            refreshPanel();
        }
    }

    @Override
    public void setDisplayState(IDisplayState state) {
        // Адаптер получает состояние и стратегию и сохраняет ее
        this.currentStrategy = state.getStrategy();
        System.out.println("Состояние вида изменено на: " + state.getName());
        refresh(); // Перерисовываем с новой стратегией
    }

    @Override
    public void refresh() {
        if (this.trajectory != null && this.controller != null) {
            displayTrajectory(this.trajectory, this.controller);
        }
    }

    private void clearPanel() {
        WidgetPool.getInstance().returnAll();
        panel.removeAll();
    }

    private void refreshPanel() {
        panel.revalidate();
        panel.repaint();
    }
}