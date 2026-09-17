package dao;

import database.Database;
import model.Movie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO {

    // CREATE
    public void addMovie(Movie movie) {

        String sql = """
                INSERT INTO movies (title, genre, language, duration, rating)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, movie.getTitle());
            statement.setString(2, movie.getGenre());
            statement.setString(3, movie.getLanguage());
            statement.setInt(4, movie.getDuration());
            statement.setDouble(5, movie.getRating());

            statement.executeUpdate();

            System.out.println("Movie added successfully!");

        } catch (SQLException e) {
            System.out.println("Failed to add movie.");
            e.printStackTrace();
        }
    }


    // READ
    public List<Movie> getAllMovies() {

        List<Movie> movies = new ArrayList<>();

        String sql = "SELECT * FROM movies";

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Movie movie = new Movie(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("genre"),
                        resultSet.getString("language"),
                        resultSet.getInt("duration"),
                        resultSet.getDouble("rating")
                );

                movies.add(movie);
            }

        } catch (SQLException e) {
            System.out.println("Failed to retrieve movies.");
            e.printStackTrace();
        }

        return movies;
    }


    // READ ONE
    public Movie getMovieById(int id) {

        String sql = "SELECT * FROM movies WHERE id = ?";

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {

                    return new Movie(
                            resultSet.getInt("id"),
                            resultSet.getString("title"),
                            resultSet.getString("genre"),
                            resultSet.getString("language"),
                            resultSet.getInt("duration"),
                            resultSet.getDouble("rating")
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Failed to find movie.");
            e.printStackTrace();
        }

        return null;
    }


    // UPDATE
    public void updateMovie(Movie movie) {

        String sql = """
                UPDATE movies
                SET title = ?, genre = ?, language = ?, duration = ?, rating = ?
                WHERE id = ?
                """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, movie.getTitle());
            statement.setString(2, movie.getGenre());
            statement.setString(3, movie.getLanguage());
            statement.setInt(4, movie.getDuration());
            statement.setDouble(5, movie.getRating());
            statement.setInt(6, movie.getId());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Movie updated successfully!");
            } else {
                System.out.println("Movie not found.");
            }

        } catch (SQLException e) {
            System.out.println("Failed to update movie.");
            e.printStackTrace();
        }
    }


    // DELETE
    public void deleteMovie(int id) {

        String sql = "DELETE FROM movies WHERE id = ?";

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Movie deleted successfully!");
            } else {
                System.out.println("Movie not found.");
            }

        } catch (SQLException e) {
            System.out.println("Failed to delete movie.");
            e.printStackTrace();
        }
    }
}