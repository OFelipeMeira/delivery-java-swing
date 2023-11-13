package UI.AppClients;

import Entities.Aplicativo;
import Entities.Database;
import Entities.Restaurant;
import UI.BasePanel;
import UI.Screen;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class PanelRestaurantList extends BasePanel {

    private final Aplicativo app;
    private final Screen screen;

    public PanelRestaurantList(Aplicativo app, Screen screen) {
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
        background.setIcon( new ImageIcon(Objects.requireNonNull(getClass().getResource("../Images/Backgrounds/UserMain.png"))));
        background.setVisible(true);

        // ---- Label for the name of the user -----
        JLabel userName = new JLabel("Welcome, " + app.getLoggedUser().getName() + "!" , SwingConstants.CENTER);
        userName.setBounds(135, 75, 320, 50);
        userName.setForeground(Color.decode("#555555"));
        userName.setFont(new Font("Arial", Font.BOLD,25));
        this.add(userName);

        // ---- Setting a button to access cart screen -----
        JButton cartBtn = new JButton("My cart");
        cartBtn.setBounds(463,64,75,75);
        cartBtn.addActionListener( e -> {
            this.screen.showUserCart();
        });
        this.add(cartBtn);

        // ---- Setting a button to Logout ---------
        JButton logoutBtn = new JButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("../Images/Buttons/Logout.png"))));
        logoutBtn.setBounds(135,615,150,50);
        logoutBtn.setBorder(null);
        logoutBtn.setBackground(Color.decode("#fcfcfc"));
        logoutBtn.addActionListener(e -> {
            this.app.setLoggedUser(null);
            this.app.setLoggedRestaurant(null);
            this.app.clearOrder();
            screen.showUserLogin();
        } );
        this.add(logoutBtn);

        // ---- Setting a button to go historic ---------
        JButton historic = new JButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("../Images/Buttons/MyHistoric.png"))));
        historic.setBounds(300,615,150,50);
        historic.addActionListener( e -> this.screen.showUserHistoric() );
        historic.setBorder(null);
        this.add(historic);

        // ---- Setting an array list to get all restaurants ---------
        ArrayList<Restaurant> restaurants = Database.getRestaurants();

        // Panel for scroll
        JPanel panel = new JPanel();
        panel.setLayout( new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        // ---- Setting a Font to be used in all frame ---------
        Font font = new Font("Arial", Font.BOLD, 13);

        // ---- Setting a label for the columns ---------
        JLabel descNames1 = new JLabel("Restaurant" , SwingConstants.CENTER);
        descNames1.setBounds(135, 105, 150, 50);
        descNames1.setForeground(Color.decode("#666666"));
        descNames1.setFont(new Font("Arial", Font.BOLD,15));
        this.add(descNames1);

        JLabel descNames2 = new JLabel("Position" , SwingConstants.CENTER);
        descNames2.setBounds(335, 105, 70, 50);
        descNames2.setForeground(Color.decode("#666666"));
        descNames2.setFont(new Font("Arial", Font.BOLD,15));
        this.add(descNames2);


        for(int i = 0; i < restaurants.size(); i++){
            // Restaurant name
            JLabel restName = new JLabel(restaurants.get(i).getName());
            restName.setBounds(0,0,200,50);
            restName.setForeground(Color.decode("#666666"));
            restName.setFont(font);

            // Restaurant position
            JLabel restLoc = new JLabel(restaurants.get(i).getPosX() + " ; " + restaurants.get(i).getPosY(), SwingConstants.CENTER );
            restLoc.setBounds(200,0,70,50);
            restLoc.setForeground(Color.decode("#666666"));
            restLoc.setFont(font);

            int auxInt = i;
            // Button to show restaurant details
            JButton restBtn = new JButton(" > ");
            restBtn.setBounds(270,0,50,50);
            restBtn.addActionListener(e -> {
                this.screen.showUserRestaurantMenu(restaurants.get(auxInt));
            });

            // container for each item
            JLabel item = new JLabel();
            item.setPreferredSize(new Dimension(320,55));
            item.setMinimumSize(new Dimension(320,55));
            item.setMaximumSize(new Dimension(320,55));
            item.setLayout(null);

            item.add(restName);
            item.add(restLoc);
            item.add(restBtn);

            panel.setBackground(Color.decode("#fcfcfc"));
            panel.add(item);
            //padding:
            panel.add(Box.createRigidArea(new Dimension(0,10)));
        }

        // ---- Setting the scroll frame -----
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        scrollPane.setBounds(135,135,320,475);

        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setUnitIncrement(8); // Scroll speed
        this.add(scrollPane);

        this.add(background);
    }
}
