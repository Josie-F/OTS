import java.util.Random;

/**
 * Venue Manager Class which extends User
 * MVC - Model
 */
public class VenueManager extends User {

  private int venueManagerId;

  /**
   * Constructor for objects of class VenueManager
   */
  public VenueManager() {
    super("VENUEMANAGER");
  }

  /**
   * Override function that sets password and username and Id
   * @param password password value from the input field
   * @param username username value from the input field
   */
  public void assignLoggedInUserVariables(String password, String username) {
    super.assignLoggedInUserVariables(password, username);
    setId();
  }

  /**
   * Function that sets Id
   */
  private void setId() {
    Random randomNumber = new Random();
    venueManagerId = randomNumber.nextInt(50);
  }
}
