package baseNoStates.clock_observers;

import java.util.ArrayList;
import java.util.List;

public abstract class Observable {
    protected List<Observer> observers = new ArrayList<>();//array to store observers, all the objects that will use the clock
    public abstract void addObserver(Observer observer);
    public abstract void removeObserver(Observer observer);
    public abstract void notifyObservers();
}
