package baseNoStates.clock_observers;

import java.time.LocalDateTime;

public interface Observer { //observer pattern, where clock is the observed
    void update(LocalDateTime date); //method that observers will use when the Clock notifies them
}
