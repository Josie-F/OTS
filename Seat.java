/**
 * Seat Class
 * MVC - Model
 */
public class Seat {

  public int id;
  //if reserved then it should be unavailable
  private String status;

  /**
   * Constructor for objects of class Seat
   *
   * @param seatId id to assign to the new seat
   */
  public Seat(int seatId) {
    id = seatId;
    status = "AVAILABLE";
  }

  /**
   * Sets seat status
   *
   * @param seatStatus status to assign to seat
   */
  public void setStatus(String seatStatus) {
    status = seatStatus;
  }

  /**
   * Gets seat status
   *
   * @return status of the seat
   */
  public String getStatus() {
    return status;
  }
}
