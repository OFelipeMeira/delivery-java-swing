package Entities;

import java.sql.Timestamp;
import java.util.ArrayList;

/** Class created to set up a structure to save orders */
public class Order {
    private final Timestamp date;
    private final double total;
    private final ArrayList<Sandwich> items;
    private final ArrayList<Restaurant> restaurants;

    public Order(Timestamp date, double total){
        this.date = date;
        this.total = total;
        this.items = new ArrayList<>();
        this.restaurants = new ArrayList<>();
    }

    /** Method to show all menu from a restaurant*/
    public void imprimirPedido(){
        for (Sandwich item : this.items){
            System.out.println(item.getName());
            System.out.println(item.getPrice());
        }
    }

    public Timestamp getDate() {
        return date;
    }
    public double getTotal() {
        return total;
    }
    public ArrayList<Sandwich> getItems() {
        return items;
    }
    public void addItem(Sandwich sandwich){
        this.items.add(sandwich);
    }
    public void addRestaurant(Restaurant restaurant){
        this.restaurants.add((restaurant));
    }
    public ArrayList<Restaurant> getRestaurants() {
        return restaurants;
    }

    public boolean containRestaurant(int id){
        for(Restaurant restaurant: this.restaurants){
            if (restaurant.getId() == id){
                return true;
            }
        }
        return false;
    }

}
