package baseNoStates.users;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

// Schedule class
public class Schedule {
  private DayOfWeek[] validDays; // stores days of the week for a valid req
  private LocalTime startHour; // stores the starting hour for a valid req
  private LocalTime endHour; // same... but with ending time
  private LocalDate startDay; // same...  but with starting day
  private LocalDate endDay;

  public Schedule(
      DayOfWeek[] validDays,
      LocalTime startHour,
      LocalTime endHour,
      LocalDate startDay,
      LocalDate endDay) {

    this.validDays = validDays;
    this.startHour = startHour;
    this.endHour = endHour;
    this.startDay = startDay;
    this.endDay = endDay;
  }

  public boolean canSendRequests(LocalDateTime now) {
    // no access (the blank group)
    if (startHour == null || endHour == null) {
      return false;
    }

    // get current day, hour and weekday
    LocalDate currentDay = now.toLocalDate();
    LocalTime currentHour = now.toLocalTime();
    DayOfWeek currentWeekDay = now.getDayOfWeek();

    // check date
    boolean validDate = !currentDay.isBefore(startDay) && !currentDay.isAfter(endDay);

    // check hour
    boolean validHour = !currentHour.isBefore(startHour) && !currentHour.isAfter(endHour);

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
}
