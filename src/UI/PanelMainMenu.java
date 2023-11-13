package UI;

import javax.swing.*;
import java.util.Objects;

/** Class to show the Main Menu */
public class PanelMainMenu extends BasePanel {
    private final Screen screen;

    public PanelMainMenu(Screen screen) {
        super(screen);
        this.screen = screen;
        this.setComponents();
    }

    public void setComponents(){
        // ---- Setting a background for the panel -----
        JLabel background = new JLabel();
        background.setBounds(0,0,this.getWidth(),this.getHeight());
        background.setIcon( new ImageIcon(Objects.requireNonNull(getClass().getResource("Images/Backgrounds/MainMenu.png"))));
        background.setVisible(true);

        // ---- Button to access client mode -----
        JButton client = new JButton( new ImageIcon(Objects.requireNonNull(getClass().getResource("Images/Buttons/Client.png"))));
        client.setBounds(160,353,199,50);
        client.setBorder(null);
        client.addActionListener( e -> this.screen.showUserLogin() );
        this.add(client);

        // ---- Button to access restaurant mode -----
        JButton restaurant = new JButton( new ImageIcon(Objects.requireNonNull(getClass().getResource("Images/Buttons/Restaurant.png"))));
        restaurant.setBounds(664,353,199,50);
        restaurant.setBorder(null);
        restaurant.addActionListener( e -> this.screen.showRestaurantLogin() );
        this.add(restaurant);

        this.add(background);
    }

}
