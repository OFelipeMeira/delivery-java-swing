package UI.AppClients;

import Entities.Aplicativo;
import Entities.Order;
import Entities.Sandwich;
import UI.Screen;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class PanelOrder extends JPanel{
    public final Aplicativo app;
    private final Screen screen;
    private final Order order;

    public PanelOrder(Aplicativo app, Screen screen, Order order) {
        //Panel config: size, position, bg color, layout to positions
        this.app = app;
        this.screen = screen;
        this.order = order;
        this.setBounds(0, 0,this.screen.getWidth()-16,this.screen.getHeight()-39);
        this.setLayout(null);

        this.setComponents();
        this.setVisible(true);
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

        // Creating a String with every restaurant name of the order, divided by "|"
        String restaurants = "";
        for (int i = 0; i <order.getRestaurants().size(); i++ ){
            restaurants += order.getRestaurants().get(i).getName() + " | ";
        }
        JLabel orderRestaurant = new JLabel(restaurants, SwingConstants.CENTER);
        orderRestaurant.setBounds(180, 75, 270, 50);
        orderRestaurant.setForeground(Color.decode("#555555"));
        orderRestaurant.setFont(new Font("Arial",Font.BOLD,20));
        this.add(orderRestaurant);

        // White Panel of scrollable frame
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(null);

        Font font = new Font("Arial",Font.BOLD,15);

        for(Sandwich sandwich: this.order.getItems()) {

            // Date of the order
            JLabel orderDate = new JLabel(sandwich.getName());
            orderDate.setBounds(10, 0, 200, 75);
            orderDate.setForeground(Color.decode("#555555"));
            orderDate.setFont(font);

            // Total from the order
            JLabel sandwichPrice = new JLabel("$"+String.valueOf(sandwich.getPrice()), SwingConstants.RIGHT);
            sandwichPrice.setBounds(170, 0, 130, 75);
            sandwichPrice.setForeground(Color.decode("#555555"));
            sandwichPrice.setFont(font);

            // Creating a container for each order info
            JLabel item = new JLabel();
            item.setPreferredSize(new Dimension(350,75));
            item.setMinimumSize(new Dimension(350,75));
            item.setMaximumSize(new Dimension(350,75));
            item.setLayout(null);
            item.setBackground(Color.WHITE);

            // adding all into 'containter'
            item.add(orderDate);
            item.add(sandwichPrice);

            // adding into scrollable frame
            panel.add(item);
            panel.add(Box.createRigidArea(new Dimension(0,2)));
        }

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBounds(135, 135, 320, 500);
        scrollPane.setBorder(null);
        this.add(scrollPane);

        this.add(background);
    }

}
