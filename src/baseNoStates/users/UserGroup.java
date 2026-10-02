package baseNoStates.users;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class UserGroup {
    private ArrayList<User> users; //stores all the users in this UserGroup
    private String[] validActions; //stores all the permitted actions by this group
    private DayOfWeek[] validDays; //stores days of the week for a valid req
    private LocalTime startHour; //stores the starting hour for a valid req
    private LocalTime endHour; //same... but with ending time
    private LocalDate startDay; //same...  but with starting day
    private LocalDate endDay;
    private String[] validDoors; //stores valid doors

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
        this.validDays = validDays;
        this.startHour = startHour;
        this.endHour = endHour;
        this.startDay = startDay;
        this.endDay = endDay;
        this.validDoors = validDoors;
    }

    public boolean canSendRequests(LocalDateTime now) {
        // no access
        if (startHour == null || endHour == null) {
            return false;
        }

        // get current day, hour and weekday
        LocalDate currentDay = now.toLocalDate();
        LocalTime currentHour = now.toLocalTime();
        DayOfWeek currentWeekDay = now.getDayOfWeek();

        // check date
        boolean validDate =
                !currentDay.isBefore(startDay) &&
                        !currentDay.isAfter(endDay);

        // check hour
        boolean validHour =
                !currentHour.isBefore(startHour) &&
                        !currentHour.isAfter(endHour);

        // check weekday
        boolean validWeekDay = false;

        for (DayOfWeek day : validDays) {
            if (day == currentWeekDay) {
                validWeekDay = true;
                break;
            }
        }

        return validDate && validHour && validWeekDay;
    }

    public boolean canDoAction(String action){
        for (String a : validActions){
            if(action.equals(a)){
                return true;
            }
        }
        return false;
    }

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
