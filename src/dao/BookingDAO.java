package dao;

import database.Database;
import model.Booking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

    /*
     * Creates a booking using a database transaction.
     *
     * Steps:
     * 1. Check that the selected seat belongs to the show's theatre.
     * 2. Check that the seat has not already been booked for this show.
     * 3. Insert the booking.
     * 4. Commit the transaction.
     *
     * If anything fails, the transaction is rolled back.
     */
    public boolean createBooking(Booking booking) {

        String seatCheckSql = """
                SELECT s.id
                FROM seats s
                JOIN shows sh ON s.theatre_id = sh.theatre_id
                WHERE s.id = ?
                AND sh.id = ?
                """;

        String bookingCheckSql = """
                SELECT id
                FROM bookings
                WHERE show_id = ?
                AND seat_id = ?
                """;

        String insertSql = """
                INSERT INTO bookings
                (customer_id, show_id, seat_id, booking_time, total_price)
                VALUES (?, ?, ?, ?, ?)
                """;

        Connection connection = null;

        try {

            connection = Database.getConnection();

            // Start transaction
            connection.setAutoCommit(false);

            // --------------------------------
            // Check seat belongs to show's theatre
            // --------------------------------

            try (PreparedStatement statement =
                         connection.prepareStatement(seatCheckSql)) {

                statement.setInt(1, booking.getSeatId());
                statement.setInt(2, booking.getShowId());

                try (ResultSet resultSet =
                             statement.executeQuery()) {

                    if (!resultSet.next()) {

                        System.out.println(
                                "Selected seat does not belong to this show's theatre."
                        );

                        connection.rollback();
                        return false;
                    }
                }
            }

            // --------------------------------
            // Check whether seat is already booked
            // --------------------------------

            try (PreparedStatement statement =
                         connection.prepareStatement(bookingCheckSql)) {

                statement.setInt(1, booking.getShowId());
                statement.setInt(2, booking.getSeatId());

                try (ResultSet resultSet =
                             statement.executeQuery()) {

                    if (resultSet.next()) {

                        System.out.println(
                                "Sorry, this seat is already booked."
                        );

                        connection.rollback();
                        return false;
                    }
                }
            }

            // --------------------------------
            // Create booking
            // --------------------------------

            try (PreparedStatement statement =
                         connection.prepareStatement(insertSql)) {

                statement.setInt(1, booking.getCustomerId());
                statement.setInt(2, booking.getShowId());
                statement.setInt(3, booking.getSeatId());
                statement.setString(4, booking.getBookingTime());
                statement.setDouble(5, booking.getTotalPrice());

                statement.executeUpdate();
            }

            // Everything succeeded
            connection.commit();

            System.out.println("Booking created successfully!");

            return true;

        } catch (SQLException e) {

            System.out.println("Booking failed.");

            if (connection != null) {

                try {
                    connection.rollback();
                    System.out.println("Transaction rolled back.");
                } catch (SQLException rollbackException) {
                    rollbackException.printStackTrace();
                }
            }

            e.printStackTrace();

            return false;

        } finally {

            if (connection != null) {

                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // --------------------------------
    // Get all bookings
    // --------------------------------

    public List<Booking> getAllBookings() {

        List<Booking> bookings = new ArrayList<>();

        String sql = """
                SELECT *
                FROM bookings
                ORDER BY id
                """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet =
                     statement.executeQuery()) {

            while (resultSet.next()) {

                Booking booking = new Booking(
                        resultSet.getInt("id"),
                        resultSet.getInt("customer_id"),
                        resultSet.getInt("show_id"),
                        resultSet.getInt("seat_id"),
                        resultSet.getString("booking_time"),
                        resultSet.getDouble("total_price")
                );

                bookings.add(booking);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Failed to retrieve bookings."
            );

            e.printStackTrace();
        }

        return bookings;
    }

    // --------------------------------
    // Get booking by ID
    // --------------------------------

    public Booking getBookingById(int id) {

        String sql = """
                SELECT *
                FROM bookings
                WHERE id = ?
                """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                if (resultSet.next()) {

                    return new Booking(
                            resultSet.getInt("id"),
                            resultSet.getInt("customer_id"),
                            resultSet.getInt("show_id"),
                            resultSet.getInt("seat_id"),
                            resultSet.getString("booking_time"),
                            resultSet.getDouble("total_price")
                    );
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Failed to find booking."
            );

            e.printStackTrace();
        }

        return null;
    }

    // --------------------------------
    // Delete booking / cancellation
    // --------------------------------

    public boolean deleteBooking(int id) {

        String sql = """
                DELETE FROM bookings
                WHERE id = ?
                """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            int rowsAffected =
                    statement.executeUpdate();

            if (rowsAffected > 0) {

                System.out.println(
                        "Booking cancelled successfully!"
                );

                return true;

            } else {

                System.out.println(
                        "Booking not found."
                );

                return false;
            }

        } catch (SQLException e) {

            System.out.println(
                    "Failed to cancel booking."
            );

            e.printStackTrace();

            return false;
        }
    }
}