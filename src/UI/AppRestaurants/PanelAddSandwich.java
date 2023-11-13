package UI.AppRestaurants;

import Entities.Aplicativo;
import Entities.Database;
import UI.Screen;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class PanelAddSandwich extends JPanel{

    private final Aplicativo app;
    private final Screen screen;

    public PanelAddSandwich(Aplicativo app, Screen screen) {
        this.app = app;
        this.screen = screen;
        this.setBounds(0, 0,this.screen.getWidth()-16,this.screen.getHeight()-39);
        this.setLayout(null);
        this.setComponents();
        this.setVisible(true);
    }
    private void setComponents(){
        JLabel background = new JLabel();
        background.setBounds(0,0,this.getWidth(),this.getHeight());
        background.setIcon( new ImageIcon(Objects.requireNonNull(getClass().getResource("../Images/Backgrounds/RestaurantAdd.png"))));
        background.setVisible(true);

        // ---- Button to go back to Edit Panel ---------
        JButton backBtn = new JButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("../Images/Buttons/IconBack.png"))));
        backBtn.setBounds(110,190,55,55);
        backBtn.addActionListener(e -> this.screen.showEditMenu(this.app.getLoggedRestaurant()) );
        backBtn.setBackground(Color.WHITE);
        backBtn.setBorder(null);
        this.add(backBtn);

        Font fText = new Font("Arial", Font.BOLD, 30);

        JTextField name = new JTextField();
        name.setBounds(120,290,300-20,68-37);
        name.setBackground(Color.decode("#f4f4f4"));
        name.setForeground(Color.decode("#555555"));
        name.setFont(fText);
        name.setBorder(null);
        this.add(name);

        JTextField price = new JTextField();
        price.setBounds(120,400,300-20,68-37);
        price.setBackground(Color.decode("#f4f4f4"));
        price.setForeground(Color.decode("#555555"));
        price.setFont(fText);
        price.setBorder(null);
        this.add(price);

        JButton addSanwichBtn = new JButton( new ImageIcon(Objects.requireNonNull(getClass().getResource("../Images/Buttons/Add.png"))));
        addSanwichBtn.setBounds(198,497,123,46);
        addSanwichBtn.setBackground(Color.decode("#fcfcfc"));
        addSanwichBtn.setBorder(null);
        addSanwichBtn.addActionListener( e ->{
            try {
                Database.addSandwich(
                        name.getText(),
                        Double.parseDouble(price.getText().replace(",",".")),
                        this.app.getLoggedRestaurant().getId()
                );
                this.screen.showEditMenu(this.app.getLoggedRestaurant());
            }catch (NumberFormatException numberFormatException){
                price.setForeground(Color.RED);
            }
        });
        this.add(addSanwichBtn);


        this.add(background);
    }

}
