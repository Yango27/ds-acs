package baseNoStates.clock_observers;

import java.util.ArrayList;
import java.util.List;

//an observer pattern allows an object (observable) to notify other objects (observers) when his state changes
//in this project we use as an observable the clock, and this clock, periodically notifies the observers
//(objects of Locked doorState which their unlock_shortly() method has been called)
//we use a custom observable and observer, instead of the java.utils ones basically because we don't need
//all the method and states that they use
public abstract class Observable {
    //array to store observers, all the objects that will use the observabloe (clock)
    protected List<Observer> observers = new ArrayList<>();
    public abstract void addObserver(Observer observer);
    public abstract void removeObserver(Observer observer);
    public abstract void notifyObservers();
}
