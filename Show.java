import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Show class
 * MVC = Model
 */
public class Show {

  public String name;
  public String id;
  public ArrayList<LocalDate> availableDates;
  public int seatsAvailable;
  public ArrayList<Seat> seats;

  /**
   * Constructor for objects of class Show
   *
   * @param showName name to assign to show
   * @param showId id to assign to show
   * @param dates dates to assign to show
   * @param availableSeats number of seats to assign to show
   */
  public Show(
    String showName,
    String showId,
    ArrayList<LocalDate> dates,
    String availableSeats
  ) {
    int seatsAssigned = Integer.parseInt(availableSeats);
    seats = new ArrayList<Seat>();

    id = showId;
    name = showName;
    seatsAvailable = Integer.parseInt(availableSeats);

    for (int i = 0; i < seatsAssigned; i++) {
      seats.add(new Seat(i + 1));
    }
    //for (LocalDate date : dates) {
    //  availableDates.add(date);
    //}
  }

  /**
   * Function to get show name
   *
   * @return String - show's name
   */
  public String getName() {
    return name;
  }

  /**
   * Function to add a seat
   *
   * @param seat - the seat to add to the seats array
   */
  public void addSeat(Seat seat) {
    seats.add(seat);
  }

  /**
   * Function to get show's seats
   *
   * @return ArrayList - show's seats
   */
  public ArrayList<Seat> getSeats() {
    return seats;
  }
}
