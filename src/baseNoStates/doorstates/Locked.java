package baseNoStates.doorstates;
import baseNoStates.Door;

public class Locked extends DoorState{
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

        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(10000);
                if (super.door.isClosed()){
                    System.out.println("Door " + super.door.getId() + " is closed again!");
                    super.door.setState(new Locked(super.door));
                }
                else{
                    System.out.println("Door " + super.door.getId() + " is propped!");
                    super.door.setState(new Propped(super.door));
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        thread.start();
    }
}
