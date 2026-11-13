package baseNoStates.doorstates;
import baseNoStates.Door;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//unlocked state, in the method we define what a unlocked door does in each action
public class Unlocked extends DoorState{
    private static final Logger logger = LoggerFactory.getLogger(Unlocked.class);

    public Unlocked(Door door){
        super(door);
    }

    @Override
    public String returnState() {
        return States.UNLOCKED;
    }

    @Override
    public void close() {
        if (!super.door.isClosed()) {
            super.door.setClosed(true);
            logger.info("Door {} closed!", super.door.getId());
        } else {
            logger.warn("Can't close door {} because it's already closed", super.door.getId());
        }
    }

    @Override
    public void lock() {
        if(super.door.isClosed()){
            logger.info("Door {} locked!", super.door.getId());
            super.door.setState(new Locked(super.door));
        }
        else{
            logger.warn("Door {}can't be locked, it's not closed!", super.door.getId());
        }
    }

    @Override
    public void open() {
        if (super.door.isClosed()) {
            logger.info("Door {} opened!", super.door.getId());
            super.door.setClosed(false);
        } else {
            logger.warn("Can't open door {} because it's already opened", super.door.getId());
        }
    }

    @Override
    public void unlock() {
        logger.warn("Can't unlock door {} because it's already unlocked", super.door.getId());
    }

    @Override
    public void shortly_unlocked() {
        logger.warn("Can't shortly unlock the door {} because it's already unlocked", super.door.getId());
    }
}

