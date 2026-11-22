package baseNoStates.doorstates;

import baseNoStates.Door;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// propped state, in the method we define what a propped door does in each action
public class Propped extends DoorState {
  public Propped(Door door) {
    super(door);
  }

  private static final Logger logger = LoggerFactory.getLogger(Propped.class);

  @Override
  public String returnState() {
    return States.PROPPED;
  }

  @Override
  public void close() {
    super.door.setClosed(true);
    super.door.setState(new Locked(super.door));
    logger.info("Door {} closed!", super.door.getId());
    logger.info("Door {} locked again!", super.door.getId());
  }

  @Override
  public void lock() {
    logger.warn("Can't lock door {} because it's propped", super.door.getId());
  }

  @Override
  public void open() {
    logger.warn("Can't open door {} because it's propped", super.door.getId());
  }

  @Override
  public void unlock() {
    logger.warn("Can't unlock door {} because it's propped", super.door.getId());
  }

  @Override
  public void unlockShortly() {
    logger.warn("Can't shortly unlock door {} because it's propped", super.door.getId());
  }
}
