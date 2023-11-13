package Entities;

/** Class created to set up a structure to save Restaurants */
public class Restaurant {
    private String name;
    private double posX;
    private double posY;
    private final int id;

    public Restaurant(String name, double locX, double locY, int id){
        this.name= name;
        this.posX = locX;
        this.posY = locY;
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getPosX() {
        return posX;
    }
    public double getPosY() {
        return posY;
    }
    public int getId() {
        return id;
    }
}
