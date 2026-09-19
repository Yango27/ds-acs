package baseNoStates.doorstates;

import baseNoStates.Door;

public class Shortly_Unlocked extends DoorState{
    public Shortly_Unlocked(Door door){
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
            System.out.println("Door " + super.door.getId() + " closed!");
        } else {
            System.out.println("Can't close door " + super.door.getId() + " because it's already closed");
        }
    }

    @Override
    public void lock() {
        System.out.println("Can't lock door " + super.door.getId() + " because it's shortly unlocked");
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
        System.out.println("Can't unlock door " + super.door.getId() + " because it's shortly unlocked");
    }

    @Override
    public void shortly_unlocked() {
        System.out.println("Can't unlock door " + super.door.getId() + " because it's shortly unlocked");
    }
}
