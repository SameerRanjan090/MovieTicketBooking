package dao;

import database.Database;
import model.Seat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SeatDAO {

    public void addSeat(Seat seat) {

        String sql = """
                INSERT INTO seats (theatre_id, seat_number, available)
                VALUES (?, ?, ?)
                """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, seat.getTheatreId());
            statement.setString(2, seat.getSeatNumber());
            statement.setInt(3, seat.isAvailable() ? 1 : 0);

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Failed to add seat.");
            e.printStackTrace();
        }
    }

    public List<Seat> getSeatsByTheatre(int theatreId) {

        List<Seat> seats = new ArrayList<>();

        String sql = """
                SELECT * FROM seats
                WHERE theatre_id = ?
                ORDER BY id
                """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, theatreId);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {

                    seats.add(new Seat(
                            resultSet.getInt("id"),
                            resultSet.getInt("theatre_id"),
                            resultSet.getString("seat_number"),
                            resultSet.getInt("available") == 1
                    ));
                }
            }

        } catch (SQLException e) {
            System.out.println("Failed to retrieve seats.");
            e.printStackTrace();
        }

        return seats;
    }

    public Seat getSeatById(int id) {

        String sql = "SELECT * FROM seats WHERE id = ?";

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {

                    return new Seat(
                            resultSet.getInt("id"),
                            resultSet.getInt("theatre_id"),
                            resultSet.getString("seat_number"),
                            resultSet.getInt("available") == 1
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Failed to find seat.");
            e.printStackTrace();
        }

        return null;
    }

    public void updateAvailability(int seatId, boolean available) {

        String sql = """
                UPDATE seats
                SET available = ?
                WHERE id = ?
                """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, available ? 1 : 0);
            statement.setInt(2, seatId);

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Failed to update seat availability.");
            e.printStackTrace();
        }
    }

    public boolean seatExists(int theatreId, String seatNumber) {

        String sql = """
                SELECT id FROM seats
                WHERE theatre_id = ? AND seat_number = ?
                """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, theatreId);
            statement.setString(2, seatNumber);

            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }

        } catch (SQLException e) {
            System.out.println("Failed to check seat.");
            e.printStackTrace();
        }

        return false;
    }
}