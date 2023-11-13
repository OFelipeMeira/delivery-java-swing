package UI.AppClients;

import Entities.Account;
import Entities.Aplicativo;
import Entities.Database;
import UI.BasePanel;
import UI.Screen;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/** Class to show the Panel Register from User Mode */
public class PanelRegisterUser extends BasePanel {
    private final Aplicativo app;
    private final Screen screen;

    public PanelRegisterUser(Aplicativo app, Screen screen) {
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
        background.setIcon( new ImageIcon(Objects.requireNonNull(getClass().getResource("../Images/Backgrounds/UserRegister.png"))));
        background.setVisible(true);

        // ---- Setting a button to go back to Login Page -----
        JButton backBtn = new JButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("../Images/IconBack.png"))));
        backBtn.setBounds(10,10,55,55);
        backBtn.addActionListener(e -> screen.showUserLogin() );
        backBtn.setBackground(Color.RED);
        backBtn.setBorder(null);
        this.add(backBtn);

        // ---- Setting a Font to be used for all TextFields -----
        Font fText = new Font("Arial", Font.BOLD, 20);

        // ---- Setting the text field for the name -----
        JTextField name = new JTextField();
        name.setBounds(83+10,176+10,300-20,63-37);
        name.setFont(fText);
        name.setForeground(Color.decode("#555555"));
        name.setBackground(Color.decode("#f4f4f4"));
        name.setBorder(null);
        this.add(name);

        // ---- Setting the text field for cpf -----
        JTextField cpf = new JTextField();
        cpf.setBounds(83+10,273+10,300-20,63-37);
        cpf.setFont(fText);
        cpf.setForeground(Color.decode("#555555"));
        cpf.setBackground(Color.decode("#f4f4f4"));
        cpf.setBorder(null);
        this.add(cpf);

        // ---- Setting the text field for username -----
        JTextField user = new JTextField();
        user.setBounds(83+10,370+10,300-20,63-37);
        user.setFont(fText);
        user.setForeground(Color.decode("#555555"));
        user.setBackground(Color.decode("#f4f4f4"));
        user.setBorder(null);
        this.add(user);

        // ---- Setting the text field for password -----
        JPasswordField password = new JPasswordField();
        password.setBounds(83+10,467+10,300-20,63-37);
        password.setFont(fText);
        password.setForeground(Color.decode("#555555"));
        password.setBackground(Color.decode("#f4f4f4"));
        password.setBorder(null);
        this.add(password);

        // ---- Setting the text field for the position in X -----
        JTextField posX = new JTextField();
        posX.setBounds(83+10,565+10,140-20,63-37);
        posX.setFont(fText);
        posX.setForeground(Color.decode("#555555"));
        posX.setBackground(Color.decode("#f4f4f4"));
        posX.setBorder(null);
        this.add(posX);

        // ---- Setting the text field for the position in Y -----
        JTextField posY = new JTextField();
        posY.setBounds(243+10,565+10,140-20,63-37);
        posY.setFont(fText);
        posY.setForeground(Color.decode("#555555"));
        posY.setBackground(Color.decode("#f4f4f4"));
        posY.setBorder(null);
        this.add(posY);

        // ---- Setting the Button to submit the registration -----
        // if the positions isn't a number, they will get red
        JButton register = new JButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("../Images/Buttons/Register.png"))));
        register.setBounds(171,672-20,123,45);
        register.setFont(fText);
        register.setForeground(Color.RED);
        register.setBackground(Color.YELLOW);
        register.setBorder(null);
        register.addActionListener( e ->{
            try {
                // Creating account - Insert into database
                Database.addAccount(
                        name.getText(),
                        user.getText(),
                        password.getText(),
                        cpf.getText(),
                        Integer.parseInt(posX.getText()),
                        Integer.parseInt(posY.getText())
                );

                // Setting this account already logged
                this.app.setLoggedUser( new Account(
                        name.getText(),
                        cpf.getText(),
                        Integer.parseInt(posX.getText()),
                        Integer.parseInt(posY.getText())
                ));

                // Showing next screen
                this.screen.showUserRestaurantList();

            }catch (NumberFormatException numberFormatException){
                posX.setForeground(Color.red);
                posY.setForeground(Color.red);
            }
        });
        this.add(register);

        this.add(background);
    }

}
