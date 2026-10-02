package baseNoStates.doorstates;
import baseNoStates.Door;
import baseNoStates.Main;

import java.time.Duration;
import java.time.LocalDateTime;

public class Locked extends DoorState{
    private LocalDateTime startDate;

    public Locked(Door door){
        super(door);
    }

    @Override
    public String returnState() {
        return States.LOCKED;
    }

    @Override
    public void close() {
        System.out.println("Can't close door " + super.door.getId() + " because it's already closed");
    }

    @Override
    public void lock() {
        System.out.println("Can't lock door " + super.door.getId() + " because it's already locked");
    }

    @Override
    public void open() {
        System.out.println("Can't open door " + super.door.getId() + " because it's locked");
    }

    @Override
    public void unlock() {
        System.out.println("Door " + super.door.getId() + " unlocked!");
        super.door.setState(new Unlocked(super.door));
    }

    @Override
    public void shortly_unlocked() {
        System.out.println("Door " + super.door.getId() + " shortly unlocked!");
        super.door.setState(new Shortly_Unlocked(super.door));

        Main.clock.addObserver(this); //add this object to observers, so clock notifies us about the time
        startDate = LocalDateTime.now(); //set the time when the timer starts from, to now (so, from now on, it counts to 10)
    }

    @Override
    public void update(LocalDateTime date) {
        //if 10 secs has passed we check if the doors its opened or not, in order to locked it again
        if (Duration.between(startDate, date).getSeconds() >= 10){
            if (super.door.isClosed()){
                System.out.println("Door " + super.door.getId() + " is locked again!");
                super.door.setState(new Locked(super.door));
            }
            else{
                System.out.println("Door " + super.door.getId() + " is propped!");
                super.door.setState(new Propped(super.door));
            }
            Main.clock.removeObserver(this);
        }
    }
}
