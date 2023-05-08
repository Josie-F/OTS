import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.*;

/**
 * ShowManager class - acts as a 'controller' for Show.
 * MVC - controller
 */
public class ShowManager {

  public ArrayList<Show> shows;
  public BookingManager bookingManager;
  private ArrayList<JCheckBox> selectedSeats;

  /**
   * Constructor for objects of class ShowManager
   *
   * @param bookingManagerInstance instance of bookingManager
   */
  public ShowManager(BookingManager bookingManagerInstance) {
    ArrayList<LocalDate> mockDates = new ArrayList<LocalDate>();
    shows = new ArrayList<Show>();
    LocalDate date1 = LocalDate.of(2023, 12, 31);
    LocalDate date2 = LocalDate.of(2023, 05, 30);
    mockDates.add(date1);
    mockDates.add(date2);
    shows.add(new Show("Evening", "1", mockDates, "5"));
    shows.add(new Show("Morning", "2", mockDates, "6"));
    shows.add(new Show("Midday", "3", mockDates, "20"));
    bookingManager = bookingManagerInstance;
  }

  /**
   * Function which creates a show
   * @param name name to assign to show
   * @param id id to assign to show
   * @param dates dates to assign to show
   * @param seats number of seats for show
   */
  public void createShow(
    String name,
    String id,
    ArrayList<LocalDate> dates,
    String seats
  ) {
    Show newShow = new Show(name, id, dates, seats);
    shows.add(newShow);
  }

  public void updateShow(int id) {
    // not implemented
  }

  public void deleteShow(int id) {
    // not implemented
  }

  /**
   * Return shows
   *
   * @return ArrayList of shows
   */
  public ArrayList<Show> getShowsInRange() {
    // Currently gets all shows - date filter has not been implemented
    return shows;
  }

  /**
   * Return show
   * @param name name of show
   *
   * @return Show
   */
  public Show getShow(Object name) {
    Show requiredShow = shows
      .stream()
      .filter(show -> show.getName().contains(name.toString()))
      .findFirst()
      .orElse(null);
    return requiredShow;
  }
}
