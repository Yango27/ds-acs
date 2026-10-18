package baseNoStates.clock_observers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

//a Clock is an observable object, it basically creates a timer that does a repeated task, which
//is to notify observers periodically (each second), it updates the observers with the actual dateTime
public class Clock extends Observable{
    private LocalDateTime date;
    private Timer timer;
    private int period; // seconds

    public Clock(int period) {
        this.period = period;
        timer = new Timer();
    }
    public void addObserver(Observer observer) {
        super.observers.add(observer);
    }
    public void removeObserver(Observer observer) {
        super.observers.remove(observer);
    }
    //notifies every observer, note that we do a copy of the array, in case it is changed when we are iterating over it
    public void notifyObservers() {
        for (Observer observer : new ArrayList<>(super.observers)) {
            observer.update(date);
        }
    }
    //each period, updates actual date, prints it and notifies
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
