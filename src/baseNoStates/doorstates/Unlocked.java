package baseNoStates.doorstates;
import baseNoStates.Door;

public class Unlocked extends DoorState{

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
            System.out.println("Door " + super.door.getId() + " closed!");
        } else {
            System.out.println("Can't close door " + super.door.getId() + " because it's already closed");
        }
    }

    @Override
    public void lock() {
        System.out.println("Door " + super.door.getId() + " locked!");
        super.door.setState(new Locked(super.door));
    }

    @Override
    public void open() {
        if (super.door.isClosed()) {
            System.out.println("Door " + super.door.getId() + " opened!");
            super.door.setClosed(false);
        } else {
            System.out.println("Can't open door " + super.door.getId() + " because it's already opened");
        }
    }

    @Override
    public void unlock() {
        System.out.println("Can't unlock door " + super.door.getId() + " because it's already unlocked");
    }

    @Override
    public void shortly_unlocked() {
        System.out.println("Can't shortly unlock the door " + super.door.getId() + " because it's already unlocked");
    }
}

