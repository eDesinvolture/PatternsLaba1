package university.model.patterns;

import university.model.patterns.IObserver;

// Наблюдатель: Интерфейс наблюдаемого
public interface ISubject {
    void addObserver(IObserver observer);
    void notifyObservers();
}