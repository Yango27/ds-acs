package baseNoStates.doorstates;

import baseNoStates.Door;
import baseNoStates.clock_observers.Clock;
import baseNoStates.clock_observers.Observer;
import java.time.Duration;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// locked state, in the method we define what a locked door does in each action
// it is also an observer, this comes from the observer pattern, explained in the class observable
// and observer
public class Locked extends DoorState implements Observer {
  private LocalDateTime startDate;
  private static final Logger logger = LoggerFactory.getLogger(Locked.class);

  public Locked(Door door) {
    super(door);
  }

  @Override
  public String returnState() {
    return States.LOCKED;
  }

  @Override
  public void close() {
    logger.warn("Can't close door {} because it's already closed", super.door.getId());
  }

  @Override
  public void lock() {
    logger.warn("Can't lock door {} because it's already locked", super.door.getId());
  }

  @Override
  public void open() {
    logger.warn("Can't open door {} because it's locked", super.door.getId());
  }

  @Override
  public void unlock() {
    logger.info("Door {} unlocked!", super.door.getId());
    super.door.setState(new Unlocked(super.door));
  }

  // here we use the singleton Clock, before, we created the clock in main, now it's used here, when
  // it need to be used, not at the beginning of the program.
  @Override
  public void unlockShortly() {
    logger.info("Door {} shortly unlocked!", super.door.getId());
    super.door.setState(new Shortly_Unlocked(super.door));
    Clock clock = Clock.getInstance();
    // add this object to observers, so clock notifies us about the time
    clock.addObserver(this);
    // set the time when the timer starts from, to now (so, from now on, it counts to 10)
    startDate = LocalDateTime.now();
  }

  // update method, used when the observable notifies it, it defines what a Locked door, which is an
  // observer, does when it's notified
  @Override
  public void update(LocalDateTime date) {
    // if 10 secs has passed we check if the doors its opened or not, in order to locked it again
    if (Duration.between(startDate, date).getSeconds() >= 10) {
      if (super.door.isClosed()) {
        logger.info("Door {} is locked again!", super.door.getId());
        super.door.setState(new Locked(super.door));
      } else {
        logger.warn("Door {} is propped!", super.door.getId());
        super.door.setState(new Propped(super.door));
      }
      Clock clock = Clock.getInstance();
      clock.removeObserver(this);
    }
  }
}
