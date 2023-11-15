package UI.AppClients;

import Entities.Aplicativo;
import Entities.Database;
import Entities.Restaurant;
import Entities.Sandwich;
import UI.BasePanel;
import UI.Screen;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class PanelRestaurantMenu extends BasePanel {

    private Aplicativo app;
    private Screen screen;
    private Restaurant restaurant;

    public PanelRestaurantMenu(Aplicativo app, Screen screen, Restaurant rest) {
        // Establishing the structure of the panel
        super(screen);

        // Receiving the App and Screen classes to update info
        this.screen = screen;
        this.app = app;
        this.restaurant = rest;

        // Method to display all components of this screen
        this.setComponents();
    }

    private void setComponents(){
        // ---- Setting a background for the panel -----
        JLabel background = new JLabel();
        background.setBounds(0,0,this.getWidth(),this.getHeight());
        background.setIcon( new ImageIcon(Objects.requireNonNull(getClass().getResource("../Images/Backgrounds/UserMain.png"))));
        background.setVisible(true);

        // ---- Button to back to Restaurant List ---------
        JButton backBtn = new JButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("../Images/Buttons/IconBack.png"))));
        backBtn.setBounds(135-8,75,53,53);
        backBtn.setBackground(Color.decode("#fcfcfc"));
        backBtn.addActionListener(e -> screen.showUserRestaurantList() );
        backBtn.setBorder(null);
        this.add(backBtn);

        // ---- restaurant name ---------
        JLabel restName = new JLabel(this.restaurant.getName(), SwingConstants.CENTER);
        restName.setBounds(180, 75, 270, 50);
        restName.setFont(new Font("Arial", Font.BOLD, 30));
        restName.setForeground(Color.decode("#555555"));
        this.add(restName);

        // ---- Setting a button to access cart screen -----
        JButton cartBtn = new JButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("../Images/Buttons/Cart.png"))));
        cartBtn.setBounds(463,70,75,75);
        cartBtn.setBorder(null);
        cartBtn.setBackground(Color.decode("#fcfcfc"));
        cartBtn.addActionListener( e -> {
            this.screen.showUserCart();
        });

        JLabel cartNumber = new JLabel(Integer.toString(this.app.getCurrentOrder().size()), SwingConstants.CENTER);
        cartNumber.setBounds(cartBtn.getX()+37, cartBtn.getY()+42, 28,28);
        cartNumber.setOpaque(true);
        cartNumber.setBackground(Color.decode("#66B8B2"));
        cartNumber.setBorder(BorderFactory.createLineBorder(Color.decode("#18837E"), 2));
        cartNumber.setFont(new Font("Arial", Font.BOLD, 18));
        cartNumber.setForeground(Color.decode("#555555"));
        this.add(cartNumber);
        this.add(cartBtn);

        // ---- Sandwich list ---------
        ArrayList<Sandwich> menu = Database.getMenu(this.restaurant.getId());

        // ---- Panel for the scroll panel ---------
        JPanel panel = new JPanel();
        panel.setLayout( new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        Font font = new Font("Arial", Font.BOLD,18);

        if (menu.size() == 0){
            // If there is no items of this restaurant
            JLabel message = new JLabel("No items founded", SwingConstants.CENTER);
            message.setFont(new Font("Arial", Font.BOLD, 25));
            message.setBounds(135, 135, 320, 450);
            message.setForeground(Color.decode("#555555"));
            this.add(message);

        }else {

            for (int i = 0; i < menu.size(); i++) {

                // Label Name
                JLabel sandwichName = new JLabel(menu.get(i).getName());
                sandwichName.setBounds(10, 0, 175, 50);
                sandwichName.setFont(font);
                sandwichName.setForeground(Color.decode("#666666"));

                // Label Price
                JLabel sandwichPrice = new JLabel("$" + menu.get(i).getPrice());
                sandwichPrice.setBounds(195, 0, 80, 50);
                sandwichPrice.setFont(font);
                sandwichPrice.setForeground(Color.decode("#666666"));

                // Button to add this item to cart
                JButton addPedido = new JButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("../Images/Buttons/IconLicking.png"))));
                addPedido.setBounds(260, 0, 55, 55);
                addPedido.setBackground(Color.decode("#fcfcfc"));
                addPedido.setBorder(null);
                int finalI = i;
                addPedido.addActionListener(e -> {
                    // Add item into cart
                    this.app.orderAddSandwich(menu.get(finalI));
                    // Reaload Page
                    this.screen.showUserRestaurantMenu(this.restaurant);
                });

                // container for each item
                JLabel item = new JLabel();
                item.setPreferredSize(new Dimension(320, 55));
                item.setMinimumSize(new Dimension(320, 55));
                item.setMaximumSize(new Dimension(320, 55));
                item.setLayout(null);

                item.add(sandwichName);
                item.add(sandwichPrice);
                item.add(addPedido);

                panel.add(item);
                //padding:
                panel.add(Box.createRigidArea(new Dimension(0, 10)));
            }
        }
        panel.setBackground(Color.decode("#fcfcfc"));


        // ---- Setting the scroll frame -----
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        scrollPane.setBounds(135,130,320,545);

        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setUnitIncrement(12);
        this.add(scrollPane);

        this.add(background);
    }
}

