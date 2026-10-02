package baseNoStates.clock_observers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Clock {
    private LocalDateTime date;
    private Timer timer;
    private int period; // seconds
    private List<Observer> observers = new ArrayList<>();//array to store observers, all the objects that will use the clock

    public Clock(int period) {
        this.period = period;
        timer = new Timer();
    }
    public void addObserver(Observer observer) {
        observers.add(observer);
    }
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }
    //notifies every observer, note that we do a copy of the array, in case it is changed when we are iterating over it
    public void notifyObservers() {
        for (Observer observer : new ArrayList<>(observers)) {
            observer.update(date);
        }
    }
    //each period, notifies and print
    public void start() {
        TimerTask repeatedTask = new TimerTask() {
            public void run() { // instance of anonymous class
                date = LocalDateTime.now();
                System.out.println("run() executed at " + date);
                notifyObservers();
            }
        };
        timer.scheduleAtFixedRate(repeatedTask, 0, 1000 * period);
    }
    public void stop() { timer.cancel(); }
    public int getPeriod() { return period; }
    public LocalDateTime getDate() { return date; }
}
