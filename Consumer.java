import java.util.Random;

/**
 * Consumer Class which extends User
 * MVC - Model
 */
public class Consumer extends User {

  private int consumerId;

  /**
   * Constructor for objects of class Consumer
   */
  public Consumer() {
    super("CONSUMER");
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
    consumerId = randomNumber.nextInt(50);
  }
}
