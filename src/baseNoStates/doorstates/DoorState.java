package baseNoStates.doorstates;

import baseNoStates.Door;

// an abstract class that represents a state of a Door, this class follows a state pattern
// basically, instead of having a large switch case in a code, to determine what to do for each
// action based to the actual state, we can use this state pattern, that assigns an object DoorState
// to the Door, representing a state, and this object knows exactly what to do calling their methods
// open, close...
public abstract class DoorState {
  protected Door door;
  protected String name;

  public DoorState(Door door) {
    this.door = door;
  }

  // methods that will be used for doing every possible action with a door
  public abstract void open();

  public abstract void close();

  public abstract void lock();

  public abstract void unlock();

  public abstract void unlockShortly();

  public abstract String returnState();
}
