package baseNoStates.clock_observers;

import java.util.ArrayList;

public interface Observable {
    public abstract void addObserver(Observer observer);
    public abstract void removeObserver(Observer observer);
    public abstract void notifyObservers();
}
