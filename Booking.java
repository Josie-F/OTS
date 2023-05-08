import java.util.ArrayList;
import java.util.Random;

/**
 * MVC - Booking Model
 */
public class Booking {

  private int bookingId;
  private ArrayList<Seat> bookedSeats = new ArrayList<Seat>();

  /**
   * Constructor for objects of class Booking
   *
   * @param seats arrayList of seats to assign to booking
   */
  public Booking(ArrayList<Seat> seats) {
    for (Seat seat : seats) {
      bookedSeats.add(seat);
      seat.setStatus("RESERVED");
    }

    Random randomNumber = new Random();
    bookingId = randomNumber.nextInt(50);
  }
}
