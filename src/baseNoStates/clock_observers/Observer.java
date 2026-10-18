package baseNoStates.clock_observers;

import java.time.LocalDateTime;
//observer pattern, where clock is the observed and
//observers are objects of Locked doorState which their unlock_shortly() method has been called
public interface Observer {
    //method that observers will use when the Clock (observable) notifies them
    public abstract void update(LocalDateTime date);
}
