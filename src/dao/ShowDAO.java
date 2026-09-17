package dao;

import database.Database;
import model.Show;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShowDAO {

    public void addShow(Show show) {

        String sql = """
                INSERT INTO shows (movie_id, theatre_id, show_time, ticket_price)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, show.getMovieId());
            statement.setInt(2, show.getTheatreId());
            statement.setString(3, show.getShowTime());
            statement.setDouble(4, show.getTicketPrice());

            statement.executeUpdate();

            System.out.println("Show added successfully!");

        } catch (SQLException e) {
            System.out.println("Failed to add show.");
            e.printStackTrace();
        }
    }

    public List<Show> getAllShows() {

        List<Show> shows = new ArrayList<>();

        String sql = "SELECT * FROM shows";

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Show show = new Show(
                        resultSet.getInt("id"),
                        resultSet.getInt("movie_id"),
                        resultSet.getInt("theatre_id"),
                        resultSet.getString("show_time"),
                        resultSet.getDouble("ticket_price")
                );

                shows.add(show);
            }

        } catch (SQLException e) {
            System.out.println("Failed to retrieve shows.");
            e.printStackTrace();
        }

        return shows;
    }

    public Show getShowById(int id) {

        String sql = "SELECT * FROM shows WHERE id = ?";

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {

                    return new Show(
                            resultSet.getInt("id"),
                            resultSet.getInt("movie_id"),
                            resultSet.getInt("theatre_id"),
                            resultSet.getString("show_time"),
                            resultSet.getDouble("ticket_price")
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Failed to find show.");
            e.printStackTrace();
        }

        return null;
    }

    public void updateShow(Show show) {

        String sql = """
                UPDATE shows
                SET movie_id = ?, theatre_id = ?, show_time = ?, ticket_price = ?
                WHERE id = ?
                """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, show.getMovieId());
            statement.setInt(2, show.getTheatreId());
            statement.setString(3, show.getShowTime());
            statement.setDouble(4, show.getTicketPrice());
            statement.setInt(5, show.getId());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Show updated successfully!");
            } else {
                System.out.println("Show not found.");
            }

        } catch (SQLException e) {
            System.out.println("Failed to update show.");
            e.printStackTrace();
        }
    }

    public void deleteShow(int id) {

        String sql = "DELETE FROM shows WHERE id = ?";

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Show deleted successfully!");
            } else {
                System.out.println("Show not found.");
            }

        } catch (SQLException e) {
            System.out.println("Failed to delete show.");
            e.printStackTrace();
        }
    }
}