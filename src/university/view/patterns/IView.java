package university.view.patterns;


import university.controller.AppController;
import university.model.LearningTrajectory;
import university.view.patterns.display.state.IDisplayState;

// Паттерн Адаптер: Целевой интерфейс, который ожидает Контроллер
public interface IView {
    void displayTrajectory(LearningTrajectory trajectory, AppController controller);
    void setDisplayState(IDisplayState state);
    void refresh();
}
