package baseNoStates;

// Before executing enable assertions :
// https://se-education.org/guides/tutorials/intellijUsefulSettings.html

import baseNoStates.clock_observers.Clock;

public class Main {
  public static final Clock clock = new Clock(1);
  public static void main(String[] args) {
    DirectoryDoors.makeDoors();
    DirectoryUsers.makeUsers();
    DirectoryAreas.makeAreas();
    clock.start();
    new WebServer();
  }
}
