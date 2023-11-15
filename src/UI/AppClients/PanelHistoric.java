package UI.AppClients;

import Entities.*;
import UI.BasePanel;
import UI.Screen;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class PanelHistoric extends BasePanel {
    public final Aplicativo app;
    private final Screen screen;
    private final ArrayList<Order> historic;

    public PanelHistoric(Aplicativo app, Screen screen) {
        // Establishing the structure of the panel
        super(screen);

        // Receiving the App and Screen classes to update info
        this.screen = screen;
        this.app = app;

        // Getting the current user historic
        this.historic = Database.getHistoric(this.app.getLoggedUser().getCpf());

        // Method to display all components of this screen
        this.setComponents();

    }

    private void setComponents(){
        // ---- Setting a background for the panel -----
        JLabel background = new JLabel();
        background.setBounds(0,0,this.getWidth(),this.getHeight());
        background.setIcon( new ImageIcon(Objects.requireNonNull(getClass().getResource("../Images/Backgrounds/UserMain.png"))));
        background.setVisible(true);

        // Back button
        JButton backBtn = new JButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("../Images/Buttons/IconBack.png"))));
        backBtn.setBounds(135-8,75,53,53);
        backBtn.setBackground(Color.decode("#fcfcfc"));
        backBtn.addActionListener(e -> screen.showUserRestaurantList() );
        backBtn.setBorder(null);
        this.add(backBtn);

        // White Panel of scrollable frame
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setBackground(Color.decode("#fcfcfc"));
        panel.setBorder(null);

        if (this.historic.size() == 0) {
            // Empty cart message
            JLabel message = new JLabel("NO HISTORIC AVAILABLE", SwingConstants.CENTER);
            message.setFont(new Font("Arial", Font.BOLD, 20));
            message.setBounds(135, 135, 320, 450);
            message.setForeground(Color.decode("#555555"));
            this.add(message);


        }else {
            for(Order order: this.historic) {
                Font font = new Font("Arial",Font.BOLD,15);

                // Creating a container for each order info
                JLabel item = new JLabel();
                item.setPreferredSize(new Dimension(350,75));
                item.setMinimumSize(new Dimension(350,75));
                item.setMaximumSize(new Dimension(350,75));
                item.setLayout(null);

                // Restaurants from the order
                String restaurants = "";
                for (int i = 0; i <order.getRestaurants().size(); i++ ){
                    restaurants += order.getRestaurants().get(i).getName() + " | ";
                }
                    JLabel orderRestaurant = new JLabel( restaurants );
                    orderRestaurant.setBounds(10, 0, 200, 75);
                    orderRestaurant.setForeground(Color.decode("#666666"));
                    orderRestaurant.setFont(font);
                    item.add(orderRestaurant);

                // Date from the order
                String date = String.valueOf(order.getDate());
                JLabel orderDate = new JLabel(date);
                orderDate.setBounds(10, 20, 200, 75);
                orderDate.setForeground(Color.decode("#666666"));
                orderDate.setFont(font);

                // Total from the order
                Double total = Math.round(order.getTotal() * 100.0) / 100.0;
                JLabel orderTotal = new JLabel("$"+String.valueOf(total) );
                orderTotal.setBounds(170, 20, 200, 75);
                orderTotal.setForeground(Color.decode("#666666"));
                orderTotal.setFont(font);

                // Button to see more about this order
                JButton details = new JButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("../Images/Buttons/IconDetails.png"))));
                details.setBounds(240, 10, 75, 65);
                details.setForeground(Color.decode("#666666"));
                details.setBackground(Color.decode("#fcfcfc"));
                details.setBorder(null);
                details.setFont(new Font("Arial",Font.BOLD,10));
                details.addActionListener(e -> screen.showOrderSandwichesList(order) );

                //addin all into 'containter'
                item.add(orderDate);
                item.add(orderTotal);
                item.add(details);

                // adding into scrollable frame
                panel.add(item);
                panel.add(Box.createRigidArea(new Dimension(0,2)));
            }
        }
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBounds(135, 135, 320, 500);
        scrollPane.setBorder(null);

        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setUnitIncrement(8);
        this.add(scrollPane);

        this.add(background);
    }

}
