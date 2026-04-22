package database;



import java.sql.*;



/**

 * Клас для роботи з базою даних MySQL

 */

public class DatabaseConnection {


// Параметри підключення

    private static final String URL = "jdbc:mysql://localhost:3306/ukraine_sites?allowPublicKeyRetrieval=true&useSSL=false";

    private static final String PASSWORD = "30102006"; // Зміна на ваш пароль


    private static Connection connection = null;


    /**

     * Отримати з'єднання з базою даних

     */

    public static Connection getConnection() {

        try {

            if (connection == null || connection.isClosed()) {

// Завантаження драйвера

                Class.forName("com.mysql.cj.jdbc.Driver");

// Створення з'єднання

                connection = DriverManager.getConnection(URL, USER, PASSWORD);

                System.out.println("Database connected successfully!");

            }

        } catch (ClassNotFoundException e) {

            System.err.println("MySQL JDBC Driver not found!");

            e.printStackTrace();

        } catch (SQLException e) {

            System.err.println("Connection failed!");

            e.printStackTrace();

        }

        return connection;

    }


    /**

     * Закрити з'єднання

     */

    public static void closeConnection() {

        try {

            if (connection != null && !connection.isClosed()) {

                connection.close();

                System.out.println("Database connection closed.");

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

}