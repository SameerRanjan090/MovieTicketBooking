package service;

import dao.TheatreDAO;
import model.Theatre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TheatreDAO {

    public void addTheatre(Theatre theatre) {

        String sql = """
                INSERT INTO theatres (name, location, total_seats)
                VALUES (?, ?, ?)
                """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, theatre.getName());
            statement.setString(2, theatre.getLocation());
            statement.setInt(3, theatre.getTotalSeats());

            statement.executeUpdate();

            System.out.println("Theatre added successfully!");

        } catch (SQLException e) {
            System.out.println("Failed to add theatre.");
            e.printStackTrace();
        }
    }

    public List<Theatre> getAllTheatres() {

        List<Theatre> theatres = new ArrayList<>();

        String sql = "SELECT * FROM theatres";

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Theatre theatre = new Theatre(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("location"),
                        resultSet.getInt("total_seats")
                );

                theatres.add(theatre);
            }

        } catch (SQLException e) {
            System.out.println("Failed to retrieve theatres.");
            e.printStackTrace();
        }

        return theatres;
    }

    public Theatre getTheatreById(int id) {

        String sql = "SELECT * FROM theatres WHERE id = ?";

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {

                    return new Theatre(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("location"),
                            resultSet.getInt("total_seats")
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Failed to find theatre.");
            e.printStackTrace();
        }

        return null;
    }

    public void updateTheatre(Theatre theatre) {

        String sql = """
                UPDATE theatres
                SET name = ?, location = ?, total_seats = ?
                WHERE id = ?
                """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, theatre.getName());
            statement.setString(2, theatre.getLocation());
            statement.setInt(3, theatre.getTotalSeats());
            statement.setInt(4, theatre.getId());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Theatre updated successfully!");
            } else {
                System.out.println("Theatre not found.");
            }

        } catch (SQLException e) {
            System.out.println("Failed to update theatre.");
            e.printStackTrace();
        }
    }

    public void deleteTheatre(int id) {

        String sql = "DELETE FROM theatres WHERE id = ?";

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Theatre deleted successfully!");
            } else {
                System.out.println("Theatre not found.");
            }

        } catch (SQLException e) {
            System.out.println("Failed to delete theatre.");
            e.printStackTrace();
        }
    }
}