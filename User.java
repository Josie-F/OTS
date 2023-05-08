
/**
 * Parent User class.
 * MVC - Model
 */
public abstract class User {

  private String username;
  private String password;
  public String name;
  private String email;
  private String phone;
  protected String type;

  /**
   * Constructor for objects of class User
   *
   * @param userType type of user account
   */
  public User(String userType) {
    username = new String();
    password = new String();
    name = new String();
    email = new String();
    phone = new String();
    type = userType;
  }

  /**
   * Function that sets password and username
   * @param password password value from the input field
   * @param username username value from the input field
   */
  public void assignLoggedInUserVariables(String pass, String loginName) {
    username = loginName;
    password = pass;
  }

  /**
   * Function that updates user's name, email and phone number
   * @param userFullName name value from the input field
   * @param userEmail email value from the input field
   * @param userPhone phone value from the input field
   */
  public void updateUser(
    String userFullName,
    String userEmail,
    String userPhone
  ) {
    name = userFullName;
    email = userEmail;
    phone = userPhone;
  }

  /**
   * Function that verifies user's username and password
   * @param providedUsername username value from the input field
   * @param providedPassword password value from the input field
   * @return boolean
   */
  public Boolean verifyDetails(
    String providedUsername,
    String providedPassword
  ) {
    return (
      username.equals(providedUsername) && password.equals(providedPassword)
    );
  }

  /**
   * Function that returns a user's name
   *
   * @return user's name
   */
  public String getName() {
    return name;
  }

  /**
   * Function that returns a user's username
   *
   * @return user's username
   */
  public String getUserName() {
    return username;
  }

  /**
   * Function that returns a user's email
   *
   * @return user's email
   */
  public String getEmail() {
    return email;
  }

  /**
   * Function that returns a user's phone number
   *
   * @return user's phone
   */
  public String getPhone() {
    return phone;
  }

  /**
   * Function that returns the user's type
   *
   * @return user's type
   */
  public String getType() {
    return type;
  }

  /**
   * Function that returns a user instance
   *
   * @return current instance of User
   */
  public User getUser() {
    return this;
  }
}
