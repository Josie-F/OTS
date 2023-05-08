import java.util.ArrayList;

/**
 * Class - BookingManager
 * MVC - controller
 */
public class BookingManager {

  public ArrayList<Booking> bookingsForCurrentSession;

  /**
   * Constructor for objects of class BookingManager
   */
  public BookingManager() {
    bookingsForCurrentSession = new ArrayList<Booking>();
  }

  /**
   * Create a new booking
   *
   * @param bookedSeats array of bookedSeats to add to booking
   */
  public void createNewBooking(ArrayList<Seat> bookedSeats) {
    bookingsForCurrentSession.add(new Booking(bookedSeats));
  }
}
