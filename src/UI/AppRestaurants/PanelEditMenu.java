package UI.AppRestaurants;

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

public class PanelEditMenu extends BasePanel {
    private Aplicativo app;
    private Screen screen;
    private Restaurant restaurant;

    public PanelEditMenu(Aplicativo app, Screen screen, Restaurant rest) {
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
        background.setIcon( new ImageIcon(Objects.requireNonNull(getClass().getResource("../Images/Backgrounds/RestaurantMain.png"))));
        background.setVisible(true);

        // ---- Button to back to Login ---------
        JButton backBtn = new JButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("../Images/Buttons/IconBack.png"))));
        backBtn.setBounds(100,70,55,55);
        backBtn.addActionListener(e -> screen.showRestaurantLogin() );
        backBtn.setBackground(Color.WHITE);
        backBtn.setBorder(null);
        this.add(backBtn);

        // ---- Button to add a new item on the menu ---------
        JButton addSandwich = new JButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("../Images/Buttons/IconAdd.png"))));
        addSandwich.setBounds(365,70,55,55);
        addSandwich.setBackground(Color.WHITE);
        addSandwich.setBorder(null);
        addSandwich.addActionListener(e -> screen.showAddSandwich() );
        this.add(addSandwich);

        // ---- restaurant name ---------
        JLabel restName = new JLabel(this.restaurant.getName(), SwingConstants.CENTER);
        restName.setBounds(135, 120, 250, 50);
        restName.setFont(new Font("Arial", Font.BOLD, 30));
        restName.setForeground(Color.decode("#555555"));
        restName.setBackground(Color.decode("#fcfcfc"));
        restName.setOpaque(true);
        this.add(restName);

        // ---- menu list ---------
        ArrayList<Sandwich> menu = Database.getMenu(this.restaurant.getId());

        // Panel for scroll
        JPanel panel = new JPanel();
        panel.setLayout( new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setBackground(Color.WHITE);

        Font font = new Font("Arial", Font.BOLD,17);

        for(int i = 0; i < menu.size(); i++) {

            // ---- Item Name ---------
            JLabel sandwichName = new JLabel(menu.get(i).getName());
            sandwichName.setBounds(10, 0, 175, 50);
            sandwichName.setFont(font);
            sandwichName.setForeground(Color.decode("#666666"));

            // ---- Item Price ---------
            JLabel sandwichPrice = new JLabel("$"+menu.get(i).getPrice());
            sandwichPrice.setBounds(195, 0, 50, 50);
            sandwichPrice.setFont(font);
            sandwichPrice.setForeground(Color.decode("#666666"));

            // ---- Button to edit item  ---------
            JButton addPedido = new JButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("../Images/Buttons/IconEdit.png"))));
            addPedido.setBounds(250,0,46,46);
            addPedido.setBackground(Color.WHITE);
            addPedido.setBorder(null);
            int finalI = i;
            addPedido.addActionListener(e -> {
                this.screen.showEditSandwich(menu.get(finalI));
            });

            // container for each item
            JLabel item = new JLabel();
            item.setPreferredSize(new Dimension(this.getWidth()-60,50));
            item.setMinimumSize(new Dimension(this.getWidth()-60,50));
            item.setMaximumSize(new Dimension(this.getWidth()-60,50));
            item.setLayout(null);

            item.add(sandwichName);
            item.add(sandwichPrice);
            item.add(addPedido);

            panel.add(item);
            //padding:
            panel.add(Box.createRigidArea(new Dimension(0,0)));
        }

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        scrollPane.setBounds(110, 175, 300, 425);

        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setUnitIncrement(8); // scroll speed

        this.add(scrollPane);

        // Logout Button
        JButton logoutBtn = new JButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("../Images/Buttons/Logout.png"))));
        logoutBtn.setBounds(115,615,150,50);
        logoutBtn.setBorder(null);
        logoutBtn.setFont(new Font("Arial", Font.BOLD,15));
        logoutBtn.setForeground(Color.decode("#555555"));
        logoutBtn.setBackground(Color.decode("#fcfcfc"));
        logoutBtn.addActionListener(e -> {
            this.app.setLoggedUser(null);
            this.app.setLoggedRestaurant(null);
            this.app.clearOrder();
            this.screen.showRestaurantLogin();
        } );
        this.add(logoutBtn);

        this.add(background);
    }
}

