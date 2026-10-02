package baseNoStates;

// Before executing enable assertions :
// https://se-education.org/guides/tutorials/intellijUsefulSettings.html

import baseNoStates.clock_observers.Clock;

public class Main {
  public static final Clock clock = new Clock(1);  //we instanciate clock, it can be accessed from any class
  public static void main(String[] args) {
    DirectoryDoors.makeDoors();
    DirectoryAreas.makeAreas();
    DirectoryUsers.makeUsers();
    clock.start();
    new WebServer();
  }
}
