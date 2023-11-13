package UI.AppRestaurants;

import Entities.Aplicativo;
import Entities.Database;
import Entities.Restaurant;
import UI.BasePanel;
import UI.Screen;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class PanelRestaurantRegister extends BasePanel {

    private final Aplicativo app;
    private final Screen screen;

    public PanelRestaurantRegister(Aplicativo app, Screen screen) {
        // Establishing the structure of the panel
        super(screen);

        // Receiving the App and Screen classes to update info
        this.screen = screen;
        this.app = app;

        // Method to display all components of this screen
        this.setComponents();
    }

    public void setComponents(){
        // ---- Setting a background for the panel -----
        JLabel background = new JLabel();
        background.setBounds(0,0,this.getWidth(),this.getHeight());
        background.setIcon( new ImageIcon(Objects.requireNonNull(getClass().getResource("../Images/Backgrounds/RestaurantRegister.png"))));
        background.setVisible(true);

        // ---- Button to back to Edit Panel ---------
        JButton backBtn = new JButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("../Images/Buttons/IconBack.png"))));
        backBtn.setBounds(10,10,55,55);
        backBtn.addActionListener(e -> this.screen.showRestaurantLogin() );
        backBtn.setBackground(Color.WHITE);
        backBtn.setBorder(null);
        this.add(backBtn);

        Font fText = new Font("Arial", Font.BOLD, 30);

        // ---- Setting the Text fields for the name -----
        JTextField name = new JTextField();
        name.setBounds(98+10,177+10,300-20,68-37);
        name.setForeground(Color.decode("#555555"));
        name.setBackground(Color.decode("#f4f4f4"));
        name.setFont(fText);
        name.setBorder(null);
        this.add(name);

        // ---- Setting the Text fields cpnj -----
        JTextField cnpj = new JTextField();
        cnpj.setBounds(98+10,274+10,300-20,68-37);
        cnpj.setForeground(Color.decode("#555555"));
        cnpj.setBackground(Color.decode("#f4f4f4"));
        cnpj.setFont(fText);
        cnpj.setBorder(null);
        this.add(cnpj);

        // ---- Setting the Text fields for login -----
        JTextField login = new JTextField();
        login.setBounds(98+10,371+10,300-20,68-37);
        login.setForeground(Color.decode("#555555"));
        login.setBackground(Color.decode("#f4f4f4"));
        login.setFont(fText);
        login.setBorder(null);
        this.add(login);

        // ---- Setting the Text fields for password -----
        JPasswordField password = new JPasswordField();
        password.setBounds(98+10,468+10,300-20,68-37);
        password.setForeground(Color.decode("#555555"));
        password.setBackground(Color.decode("#f4f4f4"));
        password.setFont(fText);
        password.setBorder(null);
        this.add(password);

        // ---- Setting the Text fields for the position in X -----
        JTextField posX = new JTextField();
        posX.setBounds(98+10,566+10,140-20,68-37);
        posX.setForeground(Color.decode("#555555"));
        posX.setBackground(Color.decode("#f4f4f4"));
        posX.setFont(fText);
        posX.setBorder(null);
        this.add(posX);

        // ---- Setting the Text fields for the position in Y -----
        JTextField posY = new JTextField();
        posY.setBounds(258+10,566+10,140-20,68-37);
        posY.setForeground(Color.decode("#555555"));
        posY.setBackground(Color.decode("#f4f4f4"));
        posY.setFont(fText);
        posY.setBorder(null);
        this.add(posY);

        // ---- Setting a button to change to register screen -----
        JButton btnRegister = new JButton( new ImageIcon(Objects.requireNonNull(getClass().getResource("../Images/Buttons/Register.png"))));
        btnRegister.setBounds(178,652,123,45);
        btnRegister.setForeground(Color.RED);
        btnRegister.setBackground(Color.WHITE);
        btnRegister.setFont(fText);
        btnRegister.setBorder(null);
        btnRegister.addActionListener( e ->{
            try {
                // Inserting new Restaurant into Database
                Database.addRestaurant(
                        name.getText(),
                        cnpj.getText(),
                        login.getText(),
                        password.getText(),
                        Double.parseDouble(posX.getText()),
                        Double.parseDouble(posY.getText())
                );

                // Creating a Restaurant object to log in
                Restaurant restaurant = new Restaurant(
                        name.getText(),
                        Double.parseDouble(posX.getText()),
                        Double.parseDouble(posY.getText()),
                        Database.getLastId("restaurants")
                );
                // Logging in
                this.app.setLoggedRestaurant(restaurant);

                // Changing screen
                this.screen.showEditMenu(restaurant);

            }catch (NumberFormatException numberFormatException){
                posX.setForeground(Color.red);
                posY.setForeground(Color.red);
            }
        });
        this.add(btnRegister);

        this.add(background);
    }

}

