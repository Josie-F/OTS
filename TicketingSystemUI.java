import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.JFrame;

/**
 * Ticketing System UI builds the application UI and provides crucial view functionality to the application.
 *
 * The application will start once a TicketingSystemUI class is created.
 * MVC - View
 */
public class TicketingSystemUI {

  private JFrame frame;
  private JLabel greetingsLabel;
  private JTextField username;
  private JTextField password;
  private String userType;

  private JMenu signInMenu;
  private JMenu managerMenu;
  private JMenu viewMenu;
  private JMenuItem signOutMenuItem;
  private JMenuItem signInMenuItem;

  public Container contentPane;
  public ShowManager showManager;
  public UserManager userManager;
  public BookingManager bookingManager;

  /**
   * Create a TicketingSystemUI and show it on screen.
   */
  public TicketingSystemUI() {
    bookingManager = new BookingManager();
    showManager = new ShowManager(bookingManager);
    userManager = new UserManager(bookingManager);

    makeFrame();
  }

  //-----------------Base Frame--------------------
  /**
   * Function which creates the frame and its content.
   */
  private void makeFrame() {
    frame = new JFrame("Online Ticketing System");
    contentPane = frame.getContentPane();

    JMenuBar menuBar = new JMenuBar();
    frame.setJMenuBar(menuBar);

    JPanel mainPanel = new JPanel(new BorderLayout(6, 6));
    greetingsLabel =
      new JLabel(
        "Welcome to The Bucks Centre for the Performing Arts - Please sign in to book your tickets for our shows"
      );
    mainPanel.add(greetingsLabel, BorderLayout.CENTER);

    //create sign in menu
    signInMenu = makeMenu(menuBar, "Log In/Register");
    signInMenuItem = makeMenuItem("Sign In", signInMenu);

    signInMenuItem.addActionListener(e -> createSignInPanel());

    signOutMenuItem = makeMenuItem("Sign Out", signInMenu);
    signOutMenuItem.setEnabled(false);
    signOutMenuItem.addActionListener(e -> {
      userManager.logOut(
        signOutMenuItem,
        signInMenuItem,
        managerMenu,
        viewMenu
      );
      frame.setContentPane(mainPanel);
      frame.setSize(700, 200);
    });

    //create venuemanager menu
    managerMenu = makeMenu(menuBar, "Manage Shows");
    JMenuItem showItem = makeMenuItem("Create Show", managerMenu);
    showItem.addActionListener(e -> createShowCreationPanelPopUp());
    managerMenu.setEnabled(false);

    //create browse menu
    viewMenu = makeMenu(menuBar, "Browse");
    JMenuItem browseShowsItem = makeMenuItem("Browse shows", viewMenu);
    browseShowsItem.addActionListener(e -> createViewShowsPanel(e));
    viewMenu.setEnabled(false);

    frame.setContentPane(mainPanel);
    frame.pack();
    frame.setSize(700, 200);
    frame.setVisible(true);
  }

  /**
   * Function to create the menu.
   *
   * @param menuBar the navigation bar
   * @param menuName name to assign to the menu tab
   *
   * @return new menu
   */
  private JMenu makeMenu(JMenuBar menuBar, String menuName) {
    JMenu menu = new JMenu(menuName);
    menuBar.add(menu);
    return menu;
  }

  /**
   * Function to create the menu items.
   *
   * @param menuItem name for new menu item
   * @param menu menu tab
   *
   * @return new menu item
   */
  private JMenuItem makeMenuItem(String menuItem, JMenu menu) {
    JMenuItem item = new JMenuItem(menuItem);
    menu.add(item);
    return item;
  }

  //-----------------Sign In and Register--------------------

  /**
   * Function that creates the sign in panel UI.
   *
   */
  public void createSignInPanel() {
    JPanel signInPanel = new JPanel(new GridLayout(0, 1));

    username = new JTextField();
    password = new JPasswordField();

    JLabel usernameLabel = new JLabel("Username");
    JLabel passwordLabel = new JLabel("Password");
    JCheckBox userTypeConsumer = new JCheckBox("CONSUMER");
    JCheckBox userTypeVenueManager = new JCheckBox("VENUEMANAGER");
    usernameLabel.setLabelFor(username);
    passwordLabel.setLabelFor(password);

    JButton signInBtn = new JButton("Sign In / Register");
    signInPanel.add(usernameLabel);
    signInPanel.add(username);
    signInPanel.add(passwordLabel);
    signInPanel.add(password);

    ArrayList<JCheckBox> userTypeCheckBoxes = new ArrayList<JCheckBox>();
    userTypeCheckBoxes.add(userTypeConsumer);
    userTypeCheckBoxes.add(userTypeVenueManager);

    JPanel typePanel = new JPanel(new GridLayout(0, 1));
    for (JCheckBox checkBox : userTypeCheckBoxes) {
      typePanel.add(checkBox);
      checkBox.addItemListener(
        new ItemListener() {
          public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == 1) {
              userType = checkBox.getText();
            }
          }
        }
      );
    }
    signInPanel.add(typePanel);
    signInPanel.add(signInBtn);
    signInBtn.addActionListener(e -> createConfirmDetailsPanel(e));

    frame.setContentPane(signInPanel);
    frame.pack();
    frame.revalidate();
    frame.setSize(400, 300);
  }

  /**
   * Function that creates the Confirm Details UI
   *
   * @param event the action event that was performed
   */
  private void createConfirmDetailsPanel(ActionEvent event) {
    JPanel confirmDetailsPanel = new JPanel(new GridLayout(0, 1));
    JTextField name = new JTextField();
    JTextField email = new JTextField();
    JTextField contact = new JTextField();

    JLabel welcomeLabel = new JLabel("Confirm your details");
    JLabel usernameLabel = new JLabel("Username");
    JLabel nameLabel = new JLabel("Name");
    JLabel emailLabel = new JLabel("Email");
    JLabel phoneLabel = new JLabel("Phone Number");

    confirmDetailsPanel.add(welcomeLabel);
    confirmDetailsPanel.add(nameLabel);
    confirmDetailsPanel.add(name);
    confirmDetailsPanel.add(emailLabel);
    confirmDetailsPanel.add(email);
    confirmDetailsPanel.add(phoneLabel);
    confirmDetailsPanel.add(contact);

    JButton confirmButton = new JButton("Confirm");
    confirmDetailsPanel.add(confirmButton);
    String scenario = userManager.confirmCustomerDetails(
      password.getText(),
      username.getText(),
      userType,
      signInMenuItem,
      managerMenu,
      viewMenu
    );

    if (scenario.equals("Returning")) {
      User returningUser = userManager.getUser();
      name.setText(returningUser.getName());
      contact.setText(returningUser.getEmail());
      email.setText(returningUser.getPhone());

      JLabel typeLabel = new JLabel(
        "Welcome Back - Your account is of type: " + returningUser.getType()
      );
      confirmDetailsPanel.add(typeLabel);
    } else if (scenario.equals("Incorrect login")) {
      JOptionPane.showMessageDialog(
        frame,
        "This user exists but the login details are incorrect. Please try again"
      );
      return;
    }

    confirmButton.addActionListener(e -> {
      userManager
        .getUser()
        .updateUser(name.getText(), email.getText(), contact.getText());
      signOutMenuItem.setEnabled(true);
      createViewShowsPanel(e);
    });

    frame.setContentPane(confirmDetailsPanel);
    frame.pack();
    frame.revalidate();
    frame.setSize(400, 400);
  }

  //-----------------Shows--------------------

  /**
   * Function that creates the ViewShows UI
   *
   * @param event the action event that was performed
   */
  private void createViewShowsPanel(ActionEvent event) {
    JPanel viewShowsPanel = new JPanel(new GridLayout(0, 1));
    JLabel userGreetingLabel = new JLabel(
      "Welcome " + userManager.getUser().getName()
    );
    JButton eventsButton = new JButton("See Events");
    JButton showButton = new JButton("See Shows");

    viewShowsPanel.add(userGreetingLabel);
    viewShowsPanel.add(eventsButton);
    viewShowsPanel.add(showButton);
    viewMenu.setEnabled(true);

    showButton.addActionListener(e -> {
      createShowListPanel(e);
    });

    frame.setContentPane(viewShowsPanel);
    frame.pack();
    frame.revalidate();
    frame.setSize(300, 300);
  }

  /**
   * Function that creates the Showlist UI
   *
   * @param event the action event that was performed
   */
  private void createShowListPanel(ActionEvent event) {
    ArrayList<Show> allShows = showManager.getShowsInRange();
    DefaultListModel<String> showListModel = new DefaultListModel<String>();
    JList<String> showList = new JList<String>(showListModel);
    JPanel listPanel = new JPanel(new BorderLayout(0, 1));
    JLabel showLabel = new JLabel("Current Shows: ");
    listPanel.add(showLabel, BorderLayout.NORTH);

    MouseListener mouse = new MouseAdapter() {
      public void mouseClicked(MouseEvent mouseEvent) {
        JList theShow = (JList) mouseEvent.getSource();
        int index = theShow.locationToIndex(mouseEvent.getPoint());
        Object show = theShow.getModel().getElementAt(index);

        createSeatPickerPanel(showManager.getShow(show));
      }
    };
    showList.addMouseListener(mouse);

    for (Show show : allShows) {
      showListModel.addElement(show.getName());
    }
    JScrollPane scrollPane = new JScrollPane(showList);
    listPanel.add(scrollPane, BorderLayout.CENTER);

    frame.setContentPane(listPanel);
    frame.pack();
    frame.revalidate();
    frame.setSize(400, 350);
  }

  /**
   * Function that creates the Pop Up for creating a new show
   *
   */
  public void createShowCreationPanelPopUp() {
    JTextField id = new JTextField(1);
    JTextField name = new JTextField(20);
    JTextField seatCount = new JTextField(20);
    ArrayList<LocalDate> dates = new ArrayList<LocalDate>();

    JPanel createShowPanel = new JPanel();
    createShowPanel.add(new JLabel("Show id (number 1-9):"));
    createShowPanel.add(id);
    createShowPanel.add(Box.createHorizontalStrut(10));
    createShowPanel.add(new JLabel("Show name:"));
    createShowPanel.add(name);
    createShowPanel.add(new JLabel("Seats count:"));
    createShowPanel.add(seatCount);

    int result = JOptionPane.showConfirmDialog(
      null,
      createShowPanel,
      "Please Enter show id and name",
      JOptionPane.OK_CANCEL_OPTION
    );
    if (result == JOptionPane.OK_OPTION) {
      showManager.createShow(
        name.getText(),
        id.getText(),
        dates,
        seatCount.getText()
      );
    }
  }

  //-----------------Seat Picker--------------------

  /**
   * Function that creates the SeatPicker UI
   *
   * @param show the given show to create seatpicker UI for
   */
  private void createSeatPickerPanel(Show show) {
    int seatColumns = 2;
    int seatrows = 2;
    ArrayList<Seat> selectedSeatObjects = new ArrayList<Seat>();
    ArrayList<JCheckBox> seatCheckBoxArray = new ArrayList<JCheckBox>();
    JPanel seatGridPanel = new JPanel(new GridLayout(seatColumns, 0));
    JPanel mainPanel = new JPanel(new BorderLayout(5, 5));
    ArrayList<JCheckBox> selectedSeats = new ArrayList<JCheckBox>();
    JPanel typePanel = new JPanel(new GridLayout(0, 1));

    JLabel sceneLocation = new JLabel("SCENE LOCATION");
    JButton confirmSeatsButton = new JButton("Book Seats and Pay");
    JPanel subPanel = new JPanel();
    subPanel.add(
      new JLabel("Select your seats for the: " + show.name + " show")
    );

    for (Seat seat : show.getSeats()) {
      JCheckBox newSeatBox = new JCheckBox("" + seat.id);
      if (seat.getStatus() == "RESERVED") {
        newSeatBox.setEnabled(false);
      }
      seatCheckBoxArray.add(newSeatBox);
      seatGridPanel.add(newSeatBox);
    }

    mainPanel.add(subPanel, BorderLayout.NORTH);
    mainPanel.add(seatGridPanel, BorderLayout.CENTER);
    mainPanel.add(confirmSeatsButton, BorderLayout.PAGE_END);

    for (JCheckBox seatCheckBox : seatCheckBoxArray) {
      seatCheckBox.addItemListener(
        new ItemListener() {
          public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == 1) {
              selectedSeats.add(seatCheckBox);
            } else {
              int index = selectedSeats.indexOf(seatCheckBox);
              selectedSeats.remove(index);
            }
          }
        }
      );
    }

    confirmSeatsButton.addActionListener(e -> {
      for (JCheckBox checkBox : selectedSeats) {
        int parsedId = Integer.parseInt(checkBox.getText());

        for (Seat seat : show.getSeats()) {
          if (seat.id == parsedId) {
            selectedSeatObjects.add(seat);
          }
        }
      }

      bookingManager.createNewBooking(selectedSeatObjects);
      selectedSeatObjects.clear();
      JOptionPane.showMessageDialog(
        null,
        "Booking confirmed - Your booking has been made!"
      );
      createBookingsPanel();
    });

    frame.setContentPane(mainPanel);
    frame.pack();
    frame.setSize(400, 300);
    frame.setVisible(true);
  }

  //-----------------Bookings--------------------

  /**
   * Function that creates the Bookings UI
   *
   */
  public void createBookingsPanel() {
    JPanel bookingPanel = new JPanel(new BorderLayout(5, 5));
    bookingPanel.add(new JLabel("Bookings:"), BorderLayout.NORTH);
    bookingPanel.add(
      new JLabel("This page has yet to be implemented"),
      BorderLayout.CENTER
    );

    frame.setContentPane(bookingPanel);
    frame.pack();
    frame.setSize(400, 300);
    frame.setVisible(true);
  }
}
