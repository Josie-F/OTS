import java.awt.Container;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.*;

/**
 * UserManager Class
 * MVC - controller
 */
public class UserManager {

  private User currentUser;
  private ArrayList<User> users = new ArrayList<User>();
  private Boolean signedIn;
  private BookingManager bookingManager;

  /**
   * Constructor for objects of class ManageUser
   * @param bookingManagerInstance instance of bookingManager
   */
  public UserManager(BookingManager bookingManagerInstance) {
    signedIn = false;
    // TODO: fetch users bookings.
    bookingManager = bookingManagerInstance;
  }

  /**
   * Function that confirms a user's log in details and returns a scenario
   * @param password password from input field
   * @param username username from input field
   * @param userType userType from input field
   * @param signInMenuItem signIn menu tab
   * @param managerMenuItem manager menu tab
   * @param viewMenuItem view menu tab
   * 
   * @return String login scenario
   */
  public String confirmCustomerDetails(
    String password,
    String username,
    String userType,
    JMenuItem signInMenuItem,
    JMenuItem managerMenuItem,
    JMenuItem viewMenuItem
  ) {
    // check if a user already exists
    for (User u : users) {
      if (u.verifyDetails(username, password)) {
        return signIn(
          u,
          signInMenuItem,
          managerMenuItem,
          viewMenuItem,
          password,
          username
        );
      } else if (u.getUserName().equals(username)) {
        return "Incorrect login";
      }
    }

    if (userType == "VENUEMANAGER") {
      currentUser =
        signUpVenueManager(password, username, signInMenuItem, managerMenuItem);
    } else /*("CONSUMER")*/{
      currentUser =
        signUpConsumer(password, username, signInMenuItem);
    }

    // Add the currentUser to the users Array
    users.add(currentUser);
    return "New";
  }

  /**
   * Function that returns User
   *
   * @return current user
   */
  public User getUser() {
    return currentUser.getUser();
  }

  /**
   * Function that logs out a user
   * 
   * @param signOutMenuItem signOut menu dropwdown
   * @param signInItem signIn menu dropwdown
   * @param managerMenu manager menu tab
   * @param browseMenu view/browse tab
   *
   */
  public void logOut(
    JMenuItem signOutMenuItem,
    JMenuItem signInItem,
    JMenu managerMenu,
    JMenu browseMenu
  ) {
    // sign out the user
    signedIn = false;
    currentUser = null;

    //set the menus to disabled/enabled
    signInItem.setEnabled(true);
    signOutMenuItem.setEnabled(false);
    managerMenu.setEnabled(false);
    browseMenu.setEnabled(false);
  }

  /**
   * Function that signs up Venue manager
   *
   * @param password password from input field
   * @param username username from input field
   * @param signInMenuItem signIn dropdown
   * @param managerMenu manager menu tab
   *
   * @return VenueManager
   */
  private User signUpVenueManager(
    String password,
    String username,
    JMenuItem signInMenuItem,
    JMenuItem managerMenu
  ) {
    VenueManager venueManager = new VenueManager();
    venueManager.assignLoggedInUserVariables(password, username);
    signedIn = true;
    signInMenuItem.setEnabled(false);
    managerMenu.setEnabled(true);

    return venueManager;
  }

  /**
   * Function that signs up Consumer
   *
   * @param password password from input field
   * @param username username from input field
   * @param signInMenuItem signIn dropdown
   * @param managerMenu manager menu tab
   *
   * @return Consumer
   */
  private User signUpConsumer(
    String password,
    String username,
    JMenuItem signInMenuItem
  ) {
    Consumer consumer = new Consumer();
    consumer.assignLoggedInUserVariables(password, username);
    signedIn = true;
    signInMenuItem.setEnabled(false);

    return consumer;
  }

  /**
   * Function that signs in a returning user
   *
   * @param user the user that needs to be signed in
   * @param signInMenuItem signIn dropdown
   * @param managerMenuItem manager menu dropdown
   * @param password password from input field
   * @param username username from input field
   *
   * @return String scenario
   */
  private String signIn(
    User user,
    JMenuItem signInMenuItem,
    JMenuItem managerMenuItem,
    JMenuItem viewMenuItem,
    String password,
    String username
  ) {
    currentUser = user;
    signedIn = true;
    //set menu items disabled / enabled
    signInMenuItem.setEnabled(false);
    viewMenuItem.setEnabled(true);
    if (user.getType() == "VENUEMANAGER") {
      managerMenuItem.setEnabled(true);
    }
    return "Returning";
  }
}
