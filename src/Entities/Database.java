package Entities;



import java.security.MessageDigest;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Database {
    public static Connection connect() throws SQLException {
        String USUARIO = "root";
//        String SENHA = "senai";
        String SENHA = "";

        String CLASSE_DRIVER = "com.mysql.cj.jdbc.Driver";
        String URL_SERVIDOR = "jdbc:mysql://127.0.0.1:3306/delivery";

        try {
            Class.forName(CLASSE_DRIVER);
            return DriverManager.getConnection(URL_SERVIDOR, USUARIO,SENHA);

        }catch (SQLException e){
            // if database is not created yet:
            URL_SERVIDOR = "jdbc:mysql://127.0.0.1:3306/";
            return DriverManager.getConnection(URL_SERVIDOR, USUARIO,SENHA);

        }catch (Exception e){
            if (e instanceof ClassNotFoundException){
                System.out.println("ERRO NO DRIVER DE CONEXÃO");
            }else {
                e.printStackTrace();
                System.out.println("VERIFIQUE SE O SERVIDOR ESTA ATIVO");
            }
            System.exit(-42);
            return null;
        }
    }

    public static void disconnect(Connection conn) throws SQLException {
        if (conn != null){
            conn.close();
        }
    }

    public static void createTables(){
        try {
            Connection conn = connect();
            Statement stmt = conn.createStatement();

            String sql = " create database if not exists delivery";
            stmt.executeUpdate(sql);

            sql = "use delivery";
            stmt.executeUpdate(sql);

            sql = "create table IF NOT EXISTS accounts(" +
                    "id int auto_increment," +
                    "name varchar(100)," +
                    "user varchar(30) unique," +
                    "password varchar(255)," +
                    "cpf varchar(11) unique," +
                    "posX real," +
                    "posY real," +
                    "constraint pk_users primary key (id)" +
                    ");";
            stmt.executeUpdate(sql);

            sql ="create table IF NOT EXISTS restaurants(" +
                    "id integer auto_increment," +
                    "name varchar(200) not null," +
                    "user varchar(30) unique," +
                    "password varchar(255)," +
                    "cnpj varchar(14) unique,"+
                    "posX integer not null," +
                    "posY integer not null," +
                    "constraint pk_restaurants primary key(id)" +
                    ");";
            stmt.executeUpdate(sql);

            sql = "create table IF NOT EXISTS sandwiches(" +
                    "id integer auto_increment," +
                    "name varchar(100) not null," +
                    "price double not null," +
                    "id_restaurant integer not null," +
                    "constraint pk_sandwiches primary key (id)," +
                    "constraint fk_restaurant foreign key (id_restaurant) references restaurants(id)" +
                    ");";
            stmt.executeUpdate(sql);

            sql	= "create table IF NOT EXISTS orders(" +
                    "id int auto_increment," +
                    "id_user int," +
                    "date datetime," +
                    "total double,"+
                    "constraint pk_order primary key (id)," +
                    "constraint fk_user foreign key (id_user) references accounts(id)" +
                    ");";
            stmt.executeUpdate(sql);

            sql = "create table IF NOT EXISTS orders_sandwiches(" +
                    "id int auto_increment," +
                    "id_order int," +
                    "id_sandwich int," +
                    "constraint pk_order_sandwich primary key (id)," +
                    "constraint fk_order foreign key (id_order) references orders(id)," +
                    "constraint fk_sandwich foreign key (id_sandwich) references sandwiches(id)" +
                    ");";
            stmt.executeUpdate(sql);

            stmt.close();
            disconnect(conn);

            System.out.println("Tables created");
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static void dropAll(){
        try {
            Connection conn = connect();
            Statement stmt = conn.createStatement();

            String sql = "drop table if exists orders_sandwiches";
            stmt.executeUpdate(sql);

            sql = "drop table if exists orders";
            stmt.executeUpdate(sql);

            sql = "drop table if exists sandwiches";
            stmt.executeUpdate(sql);

            sql = "drop table if exists restaurants";
            stmt.executeUpdate(sql);

            sql = "drop table if exists accounts";
            stmt.executeUpdate(sql);

            stmt.close();
            disconnect(conn);
            System.out.println("ALL DATABASE DROPPED");
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static void addAccount(String name, String user, String password, String cpf, double posX, double posY){

        String sql = "INSERT INTO accounts (name, user, password, cpf, posX, posY)" +
                     "VALUES (?,?,?,?,?,?)";

        try{
            //HASHING PASSWORD
            MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
            byte messageDigestSenhaAdmin[] = algorithm.digest(password.getBytes("UTF-8"));

            StringBuilder hexStringPassword = new StringBuilder();
            for (byte b : messageDigestSenhaAdmin) {
                hexStringPassword.append(String.format("%02X", 0xFF & b));
            }
            String hashedPassword = hexStringPassword.toString();

            // Connecting to database
            Connection conn = connect();
            PreparedStatement salvar  = conn.prepareStatement(sql);

            salvar.setString(1, name);
            salvar.setString(2, user);
            salvar.setString(3, hashedPassword);
            salvar.setString(4, cpf);
            salvar.setDouble(5, posX);
            salvar.setDouble(6, posY);

            salvar.executeUpdate();

            salvar.close();
            disconnect(conn);

            System.out.println("Account registered");

        }catch (Exception e){
            e.printStackTrace();
            System.err.println("ERROR IN SAVING NEW USER");
            System.exit(-42);
        }
    }

    public static void addRestaurant(String name, String cnpj, String user, String password, double posX, double posY){

        String sql = "INSERT INTO restaurants (name, cnpj, user, password, posX, posY)" +
                "VALUES (?,?,?,?,?,?)";

        try{
            //HASHING PASSWORD
            MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
            byte messageDigestSenhaAdmin[] = algorithm.digest(password.getBytes("UTF-8"));

            StringBuilder hexStringPassword = new StringBuilder();
            for (byte b : messageDigestSenhaAdmin) {
                hexStringPassword.append(String.format("%02X", 0xFF & b));
            }
            String hashedPassword = hexStringPassword.toString();

            // Connecting to database
            Connection conn = connect();
            PreparedStatement stmt  = conn.prepareStatement(sql);

            stmt.setString(1, name);
            stmt.setString(2, cnpj.replace(".","").replace("-","").replace("/",""));
            stmt.setString(3, user);
            stmt.setString(4, hashedPassword);
            stmt.setDouble(5, posX);
            stmt.setDouble(6, posY);

            stmt.executeUpdate();

            stmt.close();
            disconnect(conn);
            System.out.println("Restaurant registered");
        }catch (Exception e){
            e.printStackTrace();
            System.err.println("ERROR IN SAVING NEW RESTAURANT");
            System.exit(-42);
        }
    }

    public static void addSandwich(String name, double price, int id_restaurant){
        String sql = "INSERT INTO sandwiches(name, price, id_restaurant)" +
                "VALUES (?,?,?)";
        try {
            Connection conn = connect();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, name);
            stmt.setDouble(2, price);
            stmt.setInt(3, id_restaurant);

            stmt.executeUpdate();

            stmt.close();
            disconnect(conn);
            System.out.println("Sandwich registered");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Account getAccount(String user, String password){
        try {
            //HASHING PASSWORD
            MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
            byte messageDigestSenhaAdmin[] = algorithm.digest(password.getBytes("UTF-8"));

            StringBuilder hexStringPassword = new StringBuilder();
            for (byte b : messageDigestSenhaAdmin) {
                hexStringPassword.append(String.format("%02X", 0xFF & b));
            }
            String hashedPassword = hexStringPassword.toString();

            // Database
            Connection conn = connect();
            PreparedStatement sql = conn.prepareStatement(" SELECT * FROM accounts WHERE user=? AND password=? ");

            sql.setString(1,user);
            sql.setString(2,hashedPassword);

            ResultSet resp = sql.executeQuery();

            if (resp.next()) {
                Account logged_user = new Account(
                                        resp.getString("name"),
                                        resp.getString("cpf"),
                                        resp.getInt("posX"),
                                        resp.getInt("posY")
                );

                sql.close();
                disconnect(conn);
                return logged_user;

            }else {
                throw new Exception("ACCOUNT NOT FOUNDED");
            }

        }catch (Exception e ){
            e.printStackTrace();
        }
        return null;
    }

    public static Restaurant getRestaurant(String user, String password){
        try {
            //HASHING PASSWORD
            MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
            byte messageDigestSenhaAdmin[] = algorithm.digest(password.getBytes("UTF-8"));

            StringBuilder hexStringPassword = new StringBuilder();
            for (byte b : messageDigestSenhaAdmin) {
                hexStringPassword.append(String.format("%02X", 0xFF & b));
            }
            String hashedPassword = hexStringPassword.toString();

            // Database
            Connection conn = connect();
            PreparedStatement sql = conn.prepareStatement(" SELECT * FROM restaurants WHERE user=? AND password=? ");

            sql.setString(1,user);
            sql.setString(2,hashedPassword);

            ResultSet resp = sql.executeQuery();

            if (resp.next()) {
                Restaurant restaurant = new Restaurant(
                            resp.getString("name"),
                            resp.getInt("posX"),
                            resp.getInt("posY"),
                            resp.getInt("id")
                );

                sql.close();
                disconnect(conn);
                return restaurant;

            }else {
                throw new Exception("RESTAURANT NOT FOUNDED");
            }

        }catch (Exception e ){
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Restaurant> getRestaurants(){
        try {
            Connection conn = connect();
            PreparedStatement sql = conn.prepareStatement("SELECT * from restaurants");
            ResultSet resp = sql.executeQuery();

            ArrayList<Restaurant> restaurantList = new ArrayList<>();
            while (resp.next()){
                Restaurant rest = new Restaurant(
                        resp.getString("name"),
                        resp.getInt("posX"),
                        resp.getInt("posY"),
                        resp.getInt("id")
                );
                restaurantList.add(rest);
            }

            sql.close();
            disconnect(conn);
            return restaurantList;

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Sandwich> getMenu(int restaurantID){
        try {
            Connection conn = connect();
            PreparedStatement sql = conn.prepareStatement(" SELECT * from sandwiches WHERE id_restaurant=? ");

            sql.setInt(1,restaurantID);

            ResultSet resp = sql.executeQuery();

            ArrayList<Sandwich> sandwichList = new ArrayList<>();
            while (resp.next()){
                Sandwich sandwich = new Sandwich(
                        resp.getString("name"),
                        resp.getDouble("price")
                );
                sandwichList.add(sandwich);
            }

            sql.close();
            disconnect(conn);
            return sandwichList;

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static void updateSandwich(String oldName,String newName,double newPrice){
        try {
            Connection conn = connect();
            PreparedStatement teste = conn.prepareStatement("SELECT * FROM sandwiches WHERE name=?");
            teste.setString(1, oldName);
            ResultSet res = teste.executeQuery();

            if (res.isBeforeFirst()) {
                String ATUALIZAR = "UPDATE sandwiches SET name=?, price=? WHERE name=?";
                PreparedStatement upd = conn.prepareStatement(ATUALIZAR);

                upd.setString(1, newName);
                upd.setDouble(2, newPrice);
                upd.setString(3, oldName);

                upd.executeUpdate();

                upd.close();
                disconnect(conn);
            }else{
                throw new Exception("no accounts with this ID");
            }

        }catch (Exception e ){
            e.printStackTrace();
            System.err.println("Erro ao atualizar o produto");
            System.exit(-42);
        }
    }

    public static void deleteSandwich(Sandwich sandwich){
        try	{
            Connection conn = connect();
            PreparedStatement sql = conn.prepareStatement("SELECT * FROM sandwiches where name=? and price=?");

            sql.setString(1, sandwich.getName());
            sql.setDouble(2, sandwich.getPrice());
            ResultSet res = sql.executeQuery();

            if (res.isBeforeFirst()) {
                PreparedStatement del = conn.prepareStatement("DELETE FROM sandwiches where name=? and price=?");
                del.setString(1, sandwich.getName());
                del.setDouble(2, sandwich.getPrice());
                del.executeUpdate();

                del.close();
                disconnect(conn);
            }else{
                throw new Exception("Invalid ID");
            }

        }catch (Exception e ){
            e.printStackTrace();
            System.err.println("Erro ao deletar");
            System.exit(-42);
        }
    }

    public static int getLastId(String table){
        try {
            Connection conn = connect();
            PreparedStatement sql = conn.prepareStatement(" SELECT id FROM "+table+" ORDER BY id DESC LIMIT 1 ");

//            sql.setString(1, table);

            ResultSet resp = sql.executeQuery();

            int auxInt = 0;
            if (resp.next()) {
                auxInt = resp.getInt(1);
            }
            sql.close();
            disconnect(conn);

            return auxInt;

        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    public static void finishOrder(ArrayList<Sandwich> order, Account account){
        try {
            Connection conn = connect();

            // Getting the user from the database
            PreparedStatement sql = conn.prepareStatement(" SELECT * FROM accounts WHERE cpf=? ");
            sql.setString(1, account.getCpf());
            ResultSet accountDB = sql.executeQuery();

            int accountID = 0;
            if(accountDB.next()) {
                accountID = accountDB.getInt("id");
            }

            // Getting the total of this order
            double totalOrder = 0;
            for (Sandwich sandwich : order){
                totalOrder += sandwich.getPrice();
            }

            // Creating new order
            if (accountID != 0 && totalOrder !=0) {
                sql = conn.prepareStatement("INSERT INTO orders(id_user, date, total) VALUES (?,?,?)");

                // today date
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
                DateFormat format = DateFormat.getTimeInstance();
                String dataFormatted = sdf.format(date) + " " + format.format(date);

                sql.setInt(1, accountID);
                sql.setString(2, dataFormatted);
                sql.setDouble(3, totalOrder);
                sql.executeUpdate();

            }else {
                throw new Exception("Account and total invalid");
            }

            // Getting id of order just created
            sql = conn.prepareStatement(" SELECT id FROM orders ORDER BY id DESC LIMIT 1 ");
            ResultSet res =  sql.executeQuery();
            int orderId = 0;
            if (res.next()) {
                orderId = res.getInt(1);
            }
            // Creating a new register into orders_sandwich for each item of the order
            sql = conn.prepareStatement("INSERT INTO orders_sandwiches(id_order, id_sandwich) VALUES (?,?)");
            for (Sandwich sandwich : order) {
                PreparedStatement sndwch  = conn.prepareStatement("SELECT * FROM sandwiches WHERE name=? AND price=?");

                sndwch.setString(1,sandwich.getName());
                sndwch.setDouble(2, sandwich.getPrice());

                ResultSet result = sndwch.executeQuery();
                int idSandwich = 0;
                if(result.next()) {
                    idSandwich = result.getInt(1);
                }else {
                    throw new Exception("No Food founded");
                }

                sql.setInt(1,orderId);
                sql.setInt(2,idSandwich);
                sql.executeUpdate();

                sndwch.close();
            }

            sql.close();
            disconnect(conn);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static ArrayList<Order> getHistoric(String accountCPF){
        try {
            ArrayList<Order> ordersArray = new ArrayList<>();

            // Database
            Connection conn = connect();
            PreparedStatement sql = conn.prepareStatement(" SELECT * FROM accounts WHERE cpf=? ");

            sql.setString(1,accountCPF);
            ResultSet account = sql.executeQuery();

            if (account.next()) {
                int accountID = account.getInt(1);
                sql = conn.prepareStatement(" SELECT * FROM orders WHERE id_user=?");
                sql.setInt(1, accountID);

                ResultSet orders = sql.executeQuery();

                while (orders.next()){
                    Order order = new Order(
                            orders.getTimestamp("date"),
                            orders.getDouble("total")
                        );

                    int orderID = orders.getInt("id");
                    sql = conn.prepareStatement(" SELECT * FROM orders_sandwiches WHERE id_order=?");
                    sql.setInt(1, orderID);
                    ResultSet order_sandwich = sql.executeQuery();

                    while (order_sandwich.next()) {
                        int sandwichID = order_sandwich.getInt("id_sandwich");
                        sql = conn.prepareStatement(" SELECT * FROM sandwiches WHERE id=? ");
                        sql.setInt(1, sandwichID);
                        ResultSet sandwich = sql.executeQuery();

                        while(sandwich.next()) {
                            order.addItem(new Sandwich(
                                    sandwich.getString("name"),
                                    sandwich.getDouble("price")
                            ));
                        }

                        sql = conn.prepareStatement(" SELECT restaurants.id, restaurants.name, restaurants.posX, restaurants.posY FROM sandwiches" +
                                                        " INNER JOIN restaurants" +
                                                        " ON sandwiches.id_restaurant = restaurants.id" +
                                                        " WHERE sandwiches.id = ?");
                        sql.setInt(1, sandwichID);
                        ResultSet restaurants = sql.executeQuery();

                        // searching for all restaurants from each sandwich
                        while(restaurants.next()) {
                            // if restaurant not added yet, added
                            if ( !order.containRestaurant( restaurants.getInt(1) ) ) {
                                order.addRestaurant(new Restaurant(
                                        restaurants.getString(2),   // name
                                        restaurants.getInt(3),      // posX
                                        restaurants.getInt(4),      // posY
                                        restaurants.getInt(1)       // id
                                ));
                            }
                        }
                    }
                    ordersArray.add(order);
                }
            }else {
                throw new Exception("Account not founded");
            }

            sql.close();
            disconnect(conn);

            return ordersArray;
        }catch (Exception e ){
            e.printStackTrace();
           return null;
        }
    }
}