package UI.AppClients;

import Entities.Aplicativo;
import Entities.Sandwich;
import UI.BasePanel;
import UI.Screen;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class PanelCart extends BasePanel {
    private final Screen screen;
    public final Aplicativo app;

    public PanelCart(Aplicativo app, Screen screen) {
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

        // ---- Button to back to Login ---------
        JButton backBtn = new JButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("../Images/IconBack.png"))));
        backBtn.setBounds(135,75,40,40);
        backBtn.addActionListener(e -> screen.showUserRestaurantList() );
        backBtn.setBorder(null);
        this.add(backBtn);

        double total = 0;

        // ---- Getting current order -----
        ArrayList<Sandwich> currentOrder = this.app.getCurrentOrder();

        // ---- Total Price Label -----
        JLabel orderPrice = new JLabel("", SwingConstants.RIGHT);
        orderPrice.setBounds(180, 75, 260, 50);
        orderPrice.setFont( new Font("Arial", Font.BOLD, 25));
        orderPrice.setForeground(Color.decode("#66B8B2"));
        orderPrice.setText("  Total: "+String.format("%.2f",total) );
        this.add(orderPrice);

        // Finish Order Button
        JButton finishOrder = new JButton( new ImageIcon(Objects.requireNonNull(getClass().getResource("../Images/Buttons/FinishOrder.png"))));
        finishOrder.setBounds(255, 610, 190, 50);
        finishOrder.setFont( new Font("Arial", Font.BOLD, 25));
        finishOrder.setBackground(Color.decode("#fcfcfc"));
        finishOrder.setForeground(Color.decode("#555555"));
        finishOrder.setBorder(null);
        finishOrder.addActionListener( e -> {
            // Finishing and Cleaning current order
            this.app.finishOrder();

            // Showing menu screen
            this.screen.showUserRestaurantList();
        });
        this.add(finishOrder);

        // White Panel of scrollable frame
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setBackground(Color.decode("#fcfcfc"));
        panel.setBorder(null);

        if (currentOrder.size() == 0){
            // Empty cart message
            JLabel message = new JLabel("EMPTY CART", SwingConstants.CENTER);
            message.setFont(new Font("Arial", Font.BOLD, 20));
            message.setBounds(135, 135, 320, 450);
            message.setForeground(Color.decode("#66B8B2"));
            finishOrder.setEnabled(false);
            this.add(message);

        }else {

            for(int i = 0; i < currentOrder.size(); i++) {

                total += currentOrder.get(i).getPrice();
                orderPrice.setText("  Total: "+String.format("%.2f",total));

                Font font = new Font("Arial",Font.BOLD,20);

                // Label Item Name
                JLabel sandwichName = new JLabel(currentOrder.get(i).getName());
                sandwichName.setBounds(10, 0, 200, 75);
                sandwichName.setForeground(Color.decode("#66666"));
                sandwichName.setFont(font);

                // Label Item Price
                JLabel sandwichPrice = new JLabel("$"+currentOrder.get(i).getPrice());
                sandwichPrice.setBounds(210, 0, 70, 75);
                sandwichPrice.setForeground(Color.decode("#66666"));
                sandwichPrice.setFont(font);

                // Remove item button
                JButton removeItem = new JButton("remove");
                removeItem.setBounds(270, 13, 50, 50);
                removeItem.setBorder(null);
                int auxInt = i;
                removeItem.addActionListener(e -> {
                    // Removing item from order
                    this.app.orderRemoveSandwich(currentOrder.get(auxInt));

                    //Reloading the page
                    this.screen.showUserCart();
                });

                // Creating a container for each sandwich info
                JLabel item = new JLabel();
                item.setPreferredSize(new Dimension(350,75));
                item.setMinimumSize(new Dimension(350,75));
                item.setMaximumSize(new Dimension(350,75));
                item.setLayout(null);
                item.setBackground(Color.decode("#fcfcfc"));

                //addin all into 'containter'
                item.add(sandwichName);
                item.add(sandwichPrice);
                item.add(removeItem);

                // adding into scrollable frame
                panel.add(item);
                panel.add(Box.createRigidArea(new Dimension(0,2)));
            }
        }
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBounds(135, 135, 320, 450);
        scrollPane.setBorder(null);
        this.add(scrollPane);

        this.add(background);
    }
}
