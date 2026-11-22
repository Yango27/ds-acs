package baseNoStates.clock_observers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// a Clock is an observable object, it basically creates a timer that does a repeated task, which
// is to notify observers periodically (each second), it updates the observers with the actual
// dateTime

// Clock is also a singleton now in the second milestone, the thing is, we only instanciate one
// object of Clock, and in order to not create it at the beginning of the execution, since, we might
// not need it then, and, to not saturate the start of the program, we use a Singleton (eager vs
// lazy initialization) also, this pattern prevents creating another instance
public class Clock extends Observable {
  private LocalDateTime date;
  private Timer timer;
  private static final int PERIOD = 1; // seconds
  private static Clock uniqueInstance;
  private static final Logger logger = LoggerFactory.getLogger(Clock.class);

  private Clock() {
    timer = new Timer();
  }

  // if created return, otherwise, create the instance
  public static synchronized Clock getInstance() {
    if (uniqueInstance == null) {
      uniqueInstance = new Clock();
      uniqueInstance.start();
    }
    return uniqueInstance;
  }

  public void addObserver(Observer observer) {
    super.observers.add(observer);
  }

  public void removeObserver(Observer observer) {
    super.observers.remove(observer);
  }

  // notifies every observer, note that we do a copy of the array, in case it is changed when we are
  // iterating over it
  public void notifyObservers() {
    for (Observer observer : new ArrayList<>(super.observers)) {
      observer.update(date);
    }
  }

  // each period, updates actual date, prints it and notifies
  private void start() {
    TimerTask repeatedTask =
        new TimerTask() {
          public void run() { // instance of anonymous class
            date = LocalDateTime.now();
            logger.debug("run() executed at {}", date);
            notifyObservers();
          }
        };
    timer.scheduleAtFixedRate(repeatedTask, 0, 1000 * PERIOD);
  }

  public void stop() {
    timer.cancel();
  }

  public int getPeriod() {
    return PERIOD;
  }

  public LocalDateTime getDate() {
    return date;
  }
}
