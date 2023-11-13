import Entities.Aplicativo;
import Entities.Database;

public class Main {
    public static void main(String[] args) {
        /** TO DO
         * Refactor icons:
         *      - back button
         *      - add new item (restaurant)
         *      - edit item (restaurant)
         *
         *      - cart icon (clients - RestaurantList)
         *      - restaurant details (clients - RestaurantList)
         *      - add to cart (clients - RestaurantMenu)
         *      - delete (clients - cart)
         *      - details (clients - historic)
         *
         */
        // To run need to create database 'delivery'
        // Database function
//        dropDatabase();
//        setupDatabase();

        // Running the app:
        new Aplicativo();
    }

    public static void setupDatabase(){
        Database.createTables();

        /* ================ Accounts =================*/
        Database.addAccount("Felipe",       "user", "","12345678911",0,0);
        Database.addAccount("Cooler Felipe","",     "","12345678912",0,1);


        /* ================ Restaurants =================*/
        // ------ Creating restaurants ------
        Database.addRestaurant("McDonald's","42.591.651/0001-43","mc","",1,1);
        Database.addRestaurant("Burger King", "13.574.594/0001-96","bk","",1,2);
        Database.addRestaurant("Giraffas", "09.055.134/0001-84","gf","",1,3);

        // ------ Creating McDonald's items ------ (True Values - November 2023)
        Database.addSandwich("Big Mac",                       2.45, 1);
        Database.addSandwich("Quarter Pounder®* with Cheese", 6.69, 1);
        Database.addSandwich("Cheeseburger",                  6.59, 1);
        Database.addSandwich("Hamburger Happy Meal",          3.69, 1);

        // ------ Creating Burger King items ------ (True Values - November 2023)
        Database.addSandwich("Whopper",                 6.49, 2);
        Database.addSandwich("Whopper Jr.",             5.29, 2);
        Database.addSandwich("Crispy Chicken Sandwich", 7.49, 2);
        Database.addSandwich("Chicken Fries",           6.79, 2);

        // ------ Creating Giraffas items ------
        // Nobody cares.... already went bankrupt

    }
    public static void dropDatabase(){
        Database.dropAll();
    }
}