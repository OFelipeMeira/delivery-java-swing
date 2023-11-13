package UI;

import Entities.Aplicativo;
import Entities.Restaurant;
import Entities.Sandwich;
import Entities.Order;

import UI.AppClients.*;
import UI.AppRestaurants.*;

import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.*;

public class Screen extends JFrame {
    private final Aplicativo app;

    /** Class created to control the changes of screens in the app
     * receives app as an argument to call the functions to set data
     * */
    public Screen(Aplicativo app) {
        super("Felipe's Delivery");
        this.setLayout(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
        this.app = app;

        // Centralizing the screen when run the app:
        this.setSize(1052,778);
        int screenWidth = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        int screenHeight = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        int startPositionWidth = (screenWidth-this.getWidth())/2;
        int startPositionHeight = (screenHeight-this.getHeight())/2;
        this.setLocation(startPositionWidth,startPositionHeight);

        // Method to show the first screen
        this.showMainMenu();
    }

    /** Method to remove all previous components, to show only the next panel */
    public void showComponent(JPanel component) {
        this.getContentPane().removeAll();
        this.repaint();
        this.setContentPane(component);
    }

    public void showMainMenu(){ showComponent(new PanelMainMenu(this));}

    // ----- Screens of the Client Mode -----
    public void showUserLogin(){
        showComponent(new PanelLogin(this.app, this));
    }
    public void showUserRegister(){showComponent(new PanelRegisterUser(this.app, this));}
    public void showUserRestaurantList(){
        showComponent(new PanelRestaurantList(this.app, this));
    }
    public void showUserRestaurantMenu(Restaurant restaurant){ showComponent( new PanelRestaurantMenu(this.app, this, restaurant));}
    public void showUserCart(){ showComponent(new PanelCart(this.app, this));}
    public void showUserHistoric(){ showComponent(new PanelHistoric(this.app, this));}
    public void showOrderSandwichesList(Order order){ showComponent(new PanelOrder(this.app, this, order));}


    // ----- Screens of the Restaurant Mode -----
    public void showRestaurantLogin(){
        showComponent(new PanelRestaurantLogin(this.app, this));
    }
    public void showRestaurantRegister(){
        showComponent(new PanelRestaurantRegister(this.app, this));
    }
    public void showEditMenu(Restaurant restaurant){
        showComponent( new PanelEditMenu(this.app, this, restaurant));
    }
    public void showEditSandwich(Sandwich sandwich){
        showComponent( new PanelEditSandwich(this.app, this, sandwich) );
    }
    public void showAddSandwich(){
        showComponent(new PanelAddSandwich(this.app, this));
    }

}


