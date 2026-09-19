package baseNoStates.doorstates;

import baseNoStates.Door;

public class Propped extends DoorState{
    public Propped(Door door){
        super(door);
    }

    @Override
    public String returnState() {
        return States.PROPPED;
    }

    @Override
    public void close() {
        super.door.setClosed(true);
        super.door.setState(new Locked(super.door));
        System.out.println("Door " + super.door.getId() + " closed!");
        System.out.println("Door " + super.door.getId() + " locked again!");
    }

    @Override
    public void lock() {
        System.out.println("Can't lock door " + super.door.getId() + " because it's propped");
    }

    @Override
    public void open() {
        System.out.println("Can't open door " + super.door.getId() + " because it's propped");
    }

    @Override
    public void unlock() {
        System.out.println("Can't unlock door " + super.door.getId() + " because it's propped");
    }

    @Override
    public void shortly_unlocked() {
        System.out.println("Can't shortly unlock door " + super.door.getId() + " because it's propped");
    }
}
