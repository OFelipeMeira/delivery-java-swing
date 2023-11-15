package Entities;

import UI.Screen;

import java.util.ArrayList;


/** Class to control all data: Restaurants, users and orders */
public class Aplicativo {
    private Account loggedUser;
    private Restaurant loggedRestaurant;
    private final ArrayList<Sandwich> currentOrder;

    public Aplicativo(){
        this.currentOrder = new ArrayList<>();

        // Start showing screens:
        new Screen(this);
    }

    public Account getLoggedUser() {
        return loggedUser;
    }
    public void setLoggedUser(Account loggedUser) {
        this.loggedUser = loggedUser;
    }
    public void orderAddSandwich(Sandwich sandwich){
        this.currentOrder.add(sandwich);
    }
    public void orderRemoveSandwich(Sandwich sandwich){
        this.currentOrder.remove(sandwich);
    }
    public ArrayList<Sandwich> getCurrentOrder(){
        return this.currentOrder;
    }

    public Restaurant getLoggedRestaurant() {
        return loggedRestaurant;
    }
    public void setLoggedRestaurant(Restaurant loggedRestaurant) {
        this.loggedRestaurant = loggedRestaurant;
    }

    public void finishOrder(){
        Database.finishOrder(this.currentOrder, this.getLoggedUser());
        this.clearOrder();
    }

    public void clearOrder(){
        this.currentOrder.clear();
    }
}
