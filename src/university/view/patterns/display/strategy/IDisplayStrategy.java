package university.view.patterns.display.strategy;


import university.controller.AppController;
import university.model.LearningTrajectory;
import javax.swing.JPanel;

public interface IDisplayStrategy {
    void draw(JPanel panel, LearningTrajectory trajectory, AppController controller);
}
