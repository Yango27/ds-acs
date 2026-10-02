package baseNoStates;

import baseNoStates.users.User;
import baseNoStates.users.UserGroup;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public final class DirectoryUsers {
  private static final ArrayList<User> users = new ArrayList<>();
  public static ArrayList<UserGroup> userGroups = new ArrayList<>();

  //adds in an array doors all the doors ID of an area given an area ID.
  private static void addAreaDoors(ArrayList<String> doors, String areaId) {
    //gets the area by ID, then, gets every door of that ID, and finally
    //get the ID of those Doors
    for (Door d : DirectoryAreas.findAreaById(areaId)
            .getDoorsGivingAccess()) {
      doors.add(d.getId());
    }
  }
  public static void makeUsers() {
    // users without any privilege, just to keep temporally users instead of deleting them,
    // this is to withdraw all permissions but still to keep user data to give back
    // permissions later
    User user1 = new User("Bernat", "12345");
    User user2 = new User("Blai", "77532");

    ArrayList<User> usersBlank = new ArrayList<>();
    usersBlank.add(user1);
    usersBlank.add(user2);

    UserGroup blank = new UserGroup(
            usersBlank,
            new String[]{},
            new DayOfWeek[]{},
            null,
            null,
            null,
            null,
            new String[]{}
    );

    users.add(user1);
    users.add(user2);


    // employees:
    // Sep. 1 this year to Mar. 1 next year
    // week days 9-17h
    // just shortly unlock
    // ground floor, floor1, exterior, stairs (this, for all),
    // that is, everywhere but the parking

    User employee1 = new User("Ernest", "74984");
    User employee2 = new User("Eulalia", "43295");

    ArrayList<User> usersEmployees = new ArrayList<>();
    usersEmployees.add(employee1);
    usersEmployees.add(employee2);

    ArrayList<String> doorsEmployees = new ArrayList<>();
    //add the doors ID of those areas valid for employees
    addAreaDoors(doorsEmployees, "groundFloor");
    addAreaDoors(doorsEmployees, "floor1");
    addAreaDoors(doorsEmployees, "exterior");
    addAreaDoors(doorsEmployees, "stairs");

    UserGroup employees = new UserGroup(
            usersEmployees,
            new String[]{
                    "unlock_shortly",
                    "open",
                    "close"
            },
            new DayOfWeek[]{
                    DayOfWeek.MONDAY,
                    DayOfWeek.TUESDAY,
                    DayOfWeek.WEDNESDAY,
                    DayOfWeek.THURSDAY,
                    DayOfWeek.FRIDAY
            },
            LocalTime.of(9, 0),
            LocalTime.of(17, 0),
            LocalDate.of(2026, 9, 1),
            LocalDate.of(2027, 3, 1),
            doorsEmployees.toArray(new String[0])
    );

    users.add(employee1);
    users.add(employee2);


    // managers:
    // Sep. 1 this year to Mar. 1 next year
    // week days + saturday, 8-20h
    // all actions
    // all spaces

    User manager1 = new User("Manel", "95783");
    User manager2 = new User("Marta", "05827");

    ArrayList<User> usersManagers = new ArrayList<>();
    usersManagers.add(manager1);
    usersManagers.add(manager2);

    ArrayList<String> doorsManagersAdmins = new ArrayList<>();
    addAreaDoors(doorsManagersAdmins, "building"); //all the spaces
    UserGroup managers = new UserGroup(
            usersManagers,
            new String[]{
                    "open",
                    "close",
                    "lock",
                    "unlock",
                    "unlock_shortly"
            },
            new DayOfWeek[]{
                    DayOfWeek.MONDAY,
                    DayOfWeek.TUESDAY,
                    DayOfWeek.WEDNESDAY,
                    DayOfWeek.THURSDAY,
                    DayOfWeek.FRIDAY,
                    DayOfWeek.SATURDAY
            },
            LocalTime.of(8, 0),
            LocalTime.of(20, 0),
            LocalDate.of(2026, 9, 1),
            LocalDate.of(2027, 3, 1),
            doorsManagersAdmins.toArray(new String[0])
    );

    users.add(manager1);
    users.add(manager2);


    // admin:
    // always = Jan. 1 this year to 2100
    // all days of the week
    // all actions
    // all spaces

    User admin = new User("Ana", "11343");

    ArrayList<User> usersAdmin = new ArrayList<>();
    usersAdmin.add(admin);

    UserGroup adminGroup = new UserGroup(
            usersAdmin,
            new String[]{
                    "open",
                    "close",
                    "lock",
                    "unlock",
                    "unlock_shortly"
            },
            new DayOfWeek[]{
                    DayOfWeek.MONDAY,
                    DayOfWeek.TUESDAY,
                    DayOfWeek.WEDNESDAY,
                    DayOfWeek.THURSDAY,
                    DayOfWeek.FRIDAY,
                    DayOfWeek.SATURDAY,
                    DayOfWeek.SUNDAY
            },
            LocalTime.of(0, 0),
            LocalTime.of(23, 59, 59),
            LocalDate.of(2026, 1, 1),
            LocalDate.of(2100, 1, 1),
            doorsManagersAdmins.toArray(new String[0])
    );

    users.add(admin);
    userGroups.add(blank);
    userGroups.add(employees);
    userGroups.add(managers);
    userGroups.add(adminGroup);
  }

  public static User findUserByCredential(String credential) {
    for (User user : users) {
      if (user.getCredential().equals(credential)) {
        return user;
      }
    }
    System.out.println("user with credential " + credential + " not found");
    return null; // otherwise we get a Java error
  }

}
