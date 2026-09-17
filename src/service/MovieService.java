package service;

import dao.MovieDAO;
import model.Movie;

import java.util.List;

public class MovieService {

    private final MovieDAO movieDAO;

    public MovieService() {
        movieDAO = new MovieDAO();
    }

    public void addMovie(String title, String genre, String language,
                         int duration, double rating) {

        if (title == null || title.isBlank()) {
            System.out.println("Movie title cannot be empty.");
            return;
        }

        if (genre == null || genre.isBlank()) {
            System.out.println("Genre cannot be empty.");
            return;
        }

        if (language == null || language.isBlank()) {
            System.out.println("Language cannot be empty.");
            return;
        }

        if (duration <= 0) {
            System.out.println("Duration must be greater than 0.");
            return;
        }

        if (rating < 0 || rating > 10) {
            System.out.println("Rating must be between 0 and 10.");
            return;
        }

        Movie movie = new Movie(
                title,
                genre,
                language,
                duration,
                rating
        );

        movieDAO.addMovie(movie);
    }

    public List<Movie> getAllMovies() {
        return movieDAO.getAllMovies();
    }

    public Movie getMovieById(int id) {
        return movieDAO.getMovieById(id);
    }

    public void updateMovie(int id, String title, String genre,
                            String language, int duration, double rating) {

        Movie existingMovie = movieDAO.getMovieById(id);

        if (existingMovie == null) {
            System.out.println("Movie not found.");
            return;
        }

        if (title == null || title.isBlank() ||
                genre == null || genre.isBlank() ||
                language == null || language.isBlank()) {

            System.out.println("Movie details cannot be empty.");
            return;
        }

        if (duration <= 0) {
            System.out.println("Duration must be greater than 0.");
            return;
        }

        if (rating < 0 || rating > 10) {
            System.out.println("Rating must be between 0 and 10.");
            return;
        }

        Movie movie = new Movie(
                id,
                title,
                genre,
                language,
                duration,
                rating
        );

        movieDAO.updateMovie(movie);
    }

    public void deleteMovie(int id) {
        Movie movie = movieDAO.getMovieById(id);

        if (movie == null) {
            System.out.println("Movie not found.");
            return;
        }

        movieDAO.deleteMovie(id);
    }
}