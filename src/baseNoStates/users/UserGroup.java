package baseNoStates.users;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

//an UserGroup groups the users by their access level of the building
//it stores the users, their valid actions, the hours and days when they can do this actions
//and the doors that they can manipulate
public class UserGroup {
    private ArrayList<User> users; //stores all the users in this UserGroup
    private String[] validActions; //stores all the permitted actions by this group
    private String[] validDoors; //stores valid doors
    private Schedule schedule;

    public UserGroup(
            ArrayList<User> users,
            String[] validActions,
            DayOfWeek[] validDays,
            LocalTime startHour,
            LocalTime endHour,
            LocalDate startDay,
            LocalDate endDay,
            String[] validDoors) {

        this.users = users;
        this.validActions = validActions;
        this.schedule = new Schedule(validDays, startHour, endHour, startDay, endDay);
        this.validDoors = validDoors;
    }

    //this method looks if the time of the request is valid given the permissions of
    //this user group
    public boolean canSendRequests(LocalDateTime now) {
        // no access (the blank group)
        return schedule.canSendRequests(now);
    }

    //this method checks if the action that they are trying to do is
    //valid for this user group
    public boolean canDoAction(String action){
        for (String a : validActions){
            if(action.equals(a)){
                return true;
            }
        }
        return false;
    }

    //this method checks if the space where they want to do this action
    //is valid for this userGroup, the way that we do this is by checking
    //if the door affected is in the group of validDoors, which is an array that
    //stores all the doors of the spaces where this group can manage
    //(we will need to get all the doors of a space (look how we do it in DirectoryUsers))
    public boolean canBeInSpace(String doorId){
        for (String d : validDoors){
            if (doorId.equals(d)){
                return true;
            }
        }
        return false;
    }

    public boolean isUserInGroup(User user){
        for (User u : users){
            if (u.getCredential().equals(user.getCredential())){
                return true;
            }
        }
        return false;
    }
}
