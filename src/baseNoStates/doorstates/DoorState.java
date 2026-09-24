package baseNoStates.doorstates;

import baseNoStates.Door;

public abstract class DoorState { //state pattern
    protected Door door;
    protected String name;

    public DoorState(Door door){
        this.door = door;
    }

    //methods that will be used for doing every possible action with a door
    public abstract void open();
    public abstract void close();
    public abstract void lock();
    public abstract void unlock();
    public abstract void shortly_unlocked();

    public abstract String returnState();

}
