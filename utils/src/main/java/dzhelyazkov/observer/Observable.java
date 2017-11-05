package dzhelyazkov.observer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public abstract class Observable {

    private final Collection<Observer> observers = new HashSet<>();

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    protected void notifyObservers() {

        for (Observer observer : new ArrayList<>(observers)) {
            observer.notify(this);
        }
    }

}
