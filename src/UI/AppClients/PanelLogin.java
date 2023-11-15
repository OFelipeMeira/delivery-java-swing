package UI.AppClients;

import Entities.Account;
import Entities.Aplicativo;
import Entities.Database;
import UI.BasePanel;
import UI.Screen;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/** Class to show the Panel Login from User Mode */
public class PanelLogin extends BasePanel {
    private final Screen screen;
    public final Aplicativo app;

    public PanelLogin(Aplicativo app, Screen screen) {
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
        background.setIcon( new ImageIcon(Objects.requireNonNull(getClass().getResource("../Images/Backgrounds/UserLogin.png"))));
        background.setVisible(true);

        // ---- Setting a Button to go back to main menu screen -----
        JButton backBtn = new JButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("../Images/Buttons/IconBack.png"))));
        backBtn.setBounds(10,10,55,55);
        backBtn.addActionListener(e -> this.screen.showMainMenu() );
        backBtn.setBackground(Color.WHITE);
        backBtn.setBorder(null);
        this.add(backBtn);

        // ---- Setting a Font to be used for all TextFields -----
        Font fText = new Font("Arial", Font.BOLD, 20);

        // ---- Setting the Text fields for userName -----
        JTextField user = new JTextField();
        user.setBounds(83+10,304+30,300-20,68-37);
        user.setFont(fText);
        user.setBorder(null);
        user.setForeground(Color.decode("#555555"));
        user.setBackground(Color.decode("#F4F4F4"));
        this.add(user);

        // ---- Setting the Text fields for password -----
        JPasswordField password = new JPasswordField();
        password.setBounds(83+10,432+9,300-20,68-37);
        password.setFont(fText);
        password.setBorder(null);
        password.setForeground(Color.decode("#555555"));
        password.setBackground(Color.decode("#F4F4F4"));
        this.add(password);

        // ---- Setting the button to submit -----
        // if the account doesn't exist, an error message will appear
        JButton login = new JButton( new ImageIcon(Objects.requireNonNull(getClass().getResource("../Images/Buttons/Login.png"))));
        login.setBounds(169,535-20,123,45);
        login.setBorder(null);
        login.addActionListener( e ->{
            Account account = Database.getAccount(user.getText(),password.getText());
            if (account != null){
                this.app.setLoggedUser(account);
                screen.showUserRestaurantList();
            }else{
                JOptionPane.showMessageDialog(this,
                        "No Account Founded",
                        "Error on Login",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        this.add(login);

        // ---- Setting a button to change to register screen -----
        JButton register = new JButton( new ImageIcon(Objects.requireNonNull(getClass().getResource("../Images/Buttons/RegisterText.png"))));
        register.setBounds(169,586-20,123,45);
        register.setFont(new Font("Arial", Font.BOLD, 20));
        register.setBorder(null);
        register.setBackground(Color.WHITE);
        register.addActionListener( e ->{
            this.screen.showUserRegister();
        });
        this.add(register);

        this.add(background);
    }

}
