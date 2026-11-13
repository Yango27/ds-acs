package baseNoStates.doorstates;
import baseNoStates.Door;
import baseNoStates.Main;
import baseNoStates.clock_observers.Observer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.LocalDateTime;

//locked state, in the method we define what a locked door does in each action
//it is also an observer, this comes from the observer pattern, explained in the class observable and observer
public class Locked extends DoorState implements Observer{
    private LocalDateTime startDate;
    private static final Logger logger = LoggerFactory.getLogger(Locked.class);

    public Locked(Door door){
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

    @Override
    public void shortly_unlocked() {
        logger.info("Door {} shortly unlocked!", super.door.getId());
        super.door.setState(new Shortly_Unlocked(super.door));

        Main.clock.addObserver(this); //add this object to observers, so clock notifies us about the time
        startDate = LocalDateTime.now(); //set the time when the timer starts from, to now (so, from now on, it counts to 10)
    }

    //update method, used when the observable notifies it, it defines what a Locked door, which is an observer, does when
    //it's notified
    @Override
    public void update(LocalDateTime date) {
        //if 10 secs has passed we check if the doors its opened or not, in order to locked it again
        if (Duration.between(startDate, date).getSeconds() >= 10){
            if (super.door.isClosed()){
                logger.info("Door {} is locked again!", super.door.getId());
                super.door.setState(new Locked(super.door));
            }
            else{
                logger.warn("Door {} is propped!", super.door.getId());
                super.door.setState(new Propped(super.door));
            }
            Main.clock.removeObserver(this);
        }
    }
}
