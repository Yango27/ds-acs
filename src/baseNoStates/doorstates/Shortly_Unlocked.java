package baseNoStates.doorstates;

import baseNoStates.Door;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// unlocked_shortly state, in the method we define what a unlocked shortly door does in each action
public class Shortly_Unlocked extends DoorState {
  private static final Logger logger = LoggerFactory.getLogger(Shortly_Unlocked.class);

  public Shortly_Unlocked(Door door) {
    super(door);
  }

  @Override
  public String returnState() {
    return States.UNLOCKED_SHORTLY;
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
    logger.warn("Can't lock door {} because it's shortly unlocked", super.door.getId());
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
    logger.warn("Can't unlock door {} because it's shortly unlocked", super.door.getId());
  }

  @Override
  public void unlockShortly() {
    logger.warn("Can't unlock door {} because it's shortly unlocked", super.door.getId());
  }
}
