package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    private static final String URL = "jdbc:sqlite:movie_booking.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void initializeDatabase() {

        String movieTable = """
                CREATE TABLE IF NOT EXISTS movies (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    title TEXT NOT NULL,
                    genre TEXT NOT NULL,
                    language TEXT NOT NULL,
                    duration INTEGER NOT NULL,
                    rating REAL
                )
                """;

        String theatreTable = """
                CREATE TABLE IF NOT EXISTS theatres (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL,
                    location TEXT NOT NULL,
                    total_seats INTEGER NOT NULL
                )
                """;

        String showTable = """
                CREATE TABLE IF NOT EXISTS shows (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    movie_id INTEGER NOT NULL,
                    theatre_id INTEGER NOT NULL,
                    show_time TEXT NOT NULL,
                    ticket_price REAL NOT NULL,
                    FOREIGN KEY (movie_id) REFERENCES movies(id),
                    FOREIGN KEY (theatre_id) REFERENCES theatres(id)
                )
                """;
        String customerTable = """
        CREATE TABLE IF NOT EXISTS customers (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            name TEXT NOT NULL,
            email TEXT NOT NULL UNIQUE,
            phone TEXT NOT NULL
        )
        """;

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            statement.execute(movieTable);
            statement.execute(theatreTable);
            statement.execute(showTable);
            statement.execute(customerTable);

            System.out.println("Database initialized successfully!");

        } catch (SQLException e) {
            System.out.println("Database initialization failed.");
            e.printStackTrace();
        }
    }
}