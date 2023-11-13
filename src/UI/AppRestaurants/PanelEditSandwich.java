package UI.AppRestaurants;

import Entities.Aplicativo;
import Entities.Database;
import Entities.Sandwich;
import UI.BasePanel;
import UI.Screen;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class PanelEditSandwich extends BasePanel {

    private Aplicativo app;
    private Screen screen;
    private Sandwich sandwich;

    public PanelEditSandwich(Aplicativo app, Screen screen, Sandwich sandwich) {
        // Establishing the structure of the panel
        super(screen);

        // Receiving the App and Screen classes to update info
        this.screen = screen;
        this.app = app;
        this.sandwich = sandwich;

        // Method to display all components of this screen
        this.setComponents();
    }
    private void setComponents(){
        // ---- Setting a background for the panel -----
        JLabel background = new JLabel();
        background.setBounds(0,0,this.getWidth(),this.getHeight());
        background.setIcon( new ImageIcon(Objects.requireNonNull(getClass().getResource("../Images/Backgrounds/RestaurantEdit.png"))));
        background.setVisible(true);

        // ---- Button to back to Edit Panel ---------
        JButton backBtn = new JButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("../Images/Buttons/IconBack.png"))));
        backBtn.setBounds(110,190,55,55);
        backBtn.addActionListener(e -> this.screen.showEditMenu(this.app.getLoggedRestaurant()) );
        backBtn.setBackground(Color.WHITE);
        backBtn.setBorder(null);
        this.add(backBtn);

        // ---- font to be used all panel ---------
        Font fText = new Font("Arial", Font.BOLD, 30);

        // ---- Item name ---------
        JTextField name = new JTextField(this.sandwich.getName());
        name.setBounds(120,320,300-20,68-37);
        name.setBackground(Color.decode("#f4f4f4"));
        name.setForeground(Color.decode("#555555"));
        name.setFont(fText);
        name.setBorder(null);
        this.add(name);

        // ---- item price ---------
        JTextField price = new JTextField(Double.toString(this.sandwich.getPrice()));
        price.setBounds(120,430,300-20,68-37);
        price.setBackground(Color.decode("#f4f4f4"));
        price.setForeground(Color.decode("#555555"));
        price.setFont(fText);
        price.setBorder(null);
        this.add(price);

        // ---- button to submit update ---------
        // if the price is valid, it will turn red
        JButton update = new JButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("../Images/Buttons/Update.png"))));
        update.setBounds(129,498,123,45);
        update.setBackground(Color.decode("#fcfcfc"));
        update.setForeground(Color.RED);
        update.setFont(new Font("Arial", Font.BOLD, 30));
        update.setBorder(null);
        update.addActionListener( e ->{
            try {
                Database.updateSandwich(
                        this.sandwich.getName(),
                        name.getText(),
                        Double.parseDouble(price.getText().replace(",", ".")));
                this.screen.showEditMenu(this.app.getLoggedRestaurant());
            }catch (NumberFormatException numberFormatException){
                price.setForeground(Color.RED);
            }
        });
        this.add(update);

        // ---- button to delete item ---------
        JButton delete = new JButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("../Images/Buttons/Delete.png"))));
        delete.setBounds(268,498,123,45);
        delete.setFont(new Font("Arial", Font.BOLD, 25));
        delete.setBackground(Color.decode("#fcfcfc"));
        delete.setBorder(null);
        delete.addActionListener( e ->{
            Sandwich sandwich = new Sandwich(
                    name.getText(),
                    Double.parseDouble(price.getText())
                    );
            Database.deleteSandwich(sandwich);
            this.screen.showEditMenu(this.app.getLoggedRestaurant());
        });
        this.add(delete);

        this.add(background);
    }
}
