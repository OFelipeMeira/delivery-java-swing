package Entities;

/** Class created to set up a structure to save items of the menu */
public class Sandwich {
    private String name;
    private double price;


    public Sandwich(String name, double price){
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getPrice() {
        return price;
    }
}
