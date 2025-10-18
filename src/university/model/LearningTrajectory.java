package university.model;

import university.model.entities.ElectiveDiscipline;
import university.model.entities.MandatoryDiscipline;
import university.model.patterns.IObserver;
import university.model.patterns.ISubject;

import java.util.ArrayList;
import java.util.List;

public class LearningTrajectory implements ISubject {
    private final List<IObserver> observers = new ArrayList<>();
    private final List<MandatoryDiscipline> mandatoryDisciplines;
    private final List<ElectiveDiscipline> availableElectives;
    private final List<ElectiveDiscipline> chosenElectives = new ArrayList<>();

    public LearningTrajectory(List<MandatoryDiscipline> mandatory, List<ElectiveDiscipline> electives) {
        this.mandatoryDisciplines = mandatory;
        this.availableElectives = electives;
    }

    public void chooseElective(ElectiveDiscipline discipline) {
        if (!chosenElectives.contains(discipline) && availableElectives.contains(discipline)) {
            chosenElectives.add(discipline);
            availableElectives.remove(discipline);
            notifyObservers(); // Уведомляем наблюдателей об изменении
        }
    }

    public void removeChosenElective(ElectiveDiscipline discipline) {
        if (chosenElectives.contains(discipline)) {
            chosenElectives.remove(discipline);
            availableElectives.add(discipline); // Возвращаем в список доступных
            notifyObservers(); // Уведомляем наблюдателей об изменении
        }
    }

    // Геттеры
    public List<MandatoryDiscipline> getMandatoryDisciplines() { return mandatoryDisciplines; }
    public List<ElectiveDiscipline> getAvailableElectives() { return availableElectives; }
    public List<ElectiveDiscipline> getChosenElectives() { return chosenElectives; }


    @Override
    public void addObserver(IObserver observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers() {
        for (IObserver observer : observers) {
            observer.update();
        }
    }
}