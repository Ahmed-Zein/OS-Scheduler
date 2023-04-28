package oserver;

import java.util.ArrayList;
import java.util.List;

public class Observable {
    private List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer channel) {
        this.observers.add(channel);
    }

    public void removeObserver(Observer channel) {
        this.observers .remove(channel);
    }

    public void update( ) {
        for (Observer observer : this.observers) {
            observer.update();
        }
    }
}
