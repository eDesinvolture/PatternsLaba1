package university.controller;

import university.model.LearningTrajectory;
import university.model.entities.ElectiveDiscipline;
import university.model.patterns.IObserver;
import university.view.MainFrame;
import university.view.patterns.IView;
import university.view.patterns.MainPanelAdapter;
import university.view.patterns.display.state.IDisplayState;
import university.view.patterns.display.state.TableViewState;

import javax.swing.JPanel;

public class AppController implements IObserver {
    private final LearningTrajectory model;
    private IView view;
    private MainFrame mainFrame; // Ссылка на главный фрейм для обновления статуса
    private IDisplayState currentState;

    public AppController(LearningTrajectory model) {
        this.model = model;
        this.model.addObserver(this); // Контроллер подписывается на изменения модели
        this.currentState = new TableViewState(); // Начальное состояние
    }

    public void setView(JPanel panel) {
        this.view = new MainPanelAdapter(panel);
    }

    public void setMainFrame(MainFrame frame) {
        this.mainFrame = frame;
    }

    public IView getView() {
        return view;
    }

    public void selectElective(ElectiveDiscipline  discipline) {
        model.chooseElective(discipline);
    }

    public void toggleDisplayState() {
        currentState = currentState.getNextState();
        view.setDisplayState(currentState);
    }


    public void removeElective(ElectiveDiscipline discipline) {
        model.removeChosenElective(discipline);
    }

    @Override
    public void update() {
        // Модель изменилась, даем команду View обновиться
        System.out.println("Контроллер получил уведомление от модели. Обновляю View...");
        view.displayTrajectory(model, this);
        if (mainFrame != null) {
            mainFrame.updateStatus();
        }
    }

    public void initialDisplay() {
        view.setDisplayState(currentState); // Сначала установим состояние/стратегию
        view.displayTrajectory(model, this);
        if (mainFrame != null) {
            mainFrame.updateStatus();
        }
    }
}