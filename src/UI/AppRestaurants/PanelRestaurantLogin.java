package UI.AppRestaurants;

import Entities.Aplicativo;
import Entities.Database;
import Entities.Restaurant;
import UI.BasePanel;
import UI.Screen;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class PanelRestaurantLogin extends BasePanel {
    public final Aplicativo app;
    private final Screen screen;

    public PanelRestaurantLogin(Aplicativo app, Screen screen) {
        // Establishing the structure of the panel
        super(screen);

        // Receiving the App and Screen classes to update info
        this.screen = screen;
        this.app = app;

        // Method to display all components of this screen
        this.setComponents();
    }
    private void setComponents(){
        // ---- Setting a background for the panel -----
        JLabel background = new JLabel();
        background.setBounds(0,0,this.getWidth(),this.getHeight());
        background.setIcon( new ImageIcon(Objects.requireNonNull(getClass().getResource("../Images/Backgrounds/RestaurantLogin.png"))));
        background.setVisible(true);

        // ---- Setting a Button to go back to main menu screen -----
        JButton backBtn = new JButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("../Images/Buttons/IconBack.png"))));
        backBtn.setBounds(10,10,55,55);
        backBtn.addActionListener(e -> this.screen.showMainMenu() );
        backBtn.setBackground(Color.WHITE);
        backBtn.setBorder(null);
        this.add(backBtn);

        Font fText = new Font("Arial", Font.BOLD, 30);

        // ---- Setting the Text fields for user -----
        JTextField user = new JTextField();
        user.setBounds(83+30,304+33,300-20,68-37);
        user.setFont(fText);
        user.setForeground(Color.decode("#555555"));
        user.setBackground(Color.decode("#f4f4f4"));
        user.setBorder(null);
        this.add(user);


        // ---- Setting the Text fields for password -----
        JPasswordField password = new JPasswordField();
        password.setBounds(83+30,417+30,300-20,68-37);
        password.setFont(fText);
        password.setForeground(Color.decode("#555555"));
        password.setBackground(Color.decode("#f4f4f4"));
        password.setOpaque(true);
        password.setBorder(null);
        this.add(password);

        // ---- Setting the button to submit -----
        // if the account doesn't exist, an error message will appear
        JButton login = new JButton( new ImageIcon(Objects.requireNonNull(getClass().getResource("../Images/Buttons/Login.png"))));
        login.setBounds(184,520,123,45);
        login.setBorder(null);
        login.addActionListener( e ->{

            // searching the restaurant with that user and password
            Restaurant restaurant = Database.getRestaurant( user.getText(), password.getText() ) ;

            // if restaurant exists, set it as logged
            if (restaurant != null){
                this.app.setLoggedRestaurant(restaurant);
                screen.showEditMenu(restaurant);
            }else{
                System.err.println("Não logou");
            }

        });
        this.add(login);

        // ---- Setting a button to change to register screen -----
        JButton register = new JButton( new ImageIcon(Objects.requireNonNull(getClass().getResource("../Images/Buttons/RegisterText.png"))));
        register.setBounds(186,566,123,45);
        register.setFont(new Font("Arial", Font.BOLD, 20));
        register.setBorder(null);
        register.setBackground(Color.decode("#ffffff"));
        register.addActionListener( e ->{
            this.screen.showRestaurantRegister();
        });
        this.add(register);

        this.add(background);
    }

}
