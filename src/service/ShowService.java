package service;

import dao.MovieDAO;
import dao.ShowDAO;
import dao.TheatreDAO;
import model.Movie;
import model.Show;
import model.Theatre;

import java.util.List;

public class ShowService {

    private final ShowDAO showDAO;
    private final MovieDAO movieDAO;
    private final TheatreDAO theatreDAO;

    public ShowService() {
        showDAO = new ShowDAO();
        movieDAO = new MovieDAO();
        theatreDAO = new TheatreDAO();
    }

    public void addShow(int movieId, int theatreId,
                        String showTime, double ticketPrice) {

        Movie movie = movieDAO.getMovieById(movieId);

        if (movie == null) {
            System.out.println("Movie not found.");
            return;
        }

        Theatre theatre = theatreDAO.getTheatreById(theatreId);

        if (theatre == null) {
            System.out.println("Theatre not found.");
            return;
        }

        if (showTime == null || showTime.isBlank()) {
            System.out.println("Show time cannot be empty.");
            return;
        }

        if (ticketPrice <= 0) {
            System.out.println("Ticket price must be greater than 0.");
            return;
        }

        Show show = new Show(
                movieId,
                theatreId,
                showTime,
                ticketPrice
        );

        showDAO.addShow(show);
    }

    public List<Show> getAllShows() {
        return showDAO.getAllShows();
    }

    public Show getShowById(int id) {
        return showDAO.getShowById(id);
    }

    public void updateShow(int id, int movieId, int theatreId,
                           String showTime, double ticketPrice) {

        Show existingShow = showDAO.getShowById(id);

        if (existingShow == null) {
            System.out.println("Show not found.");
            return;
        }

        Movie movie = movieDAO.getMovieById(movieId);

        if (movie == null) {
            System.out.println("Movie not found.");
            return;
        }

        Theatre theatre = theatreDAO.getTheatreById(theatreId);

        if (theatre == null) {
            System.out.println("Theatre not found.");
            return;
        }

        if (showTime == null || showTime.isBlank()) {
            System.out.println("Show time cannot be empty.");
            return;
        }

        if (ticketPrice <= 0) {
            System.out.println("Ticket price must be greater than 0.");
            return;
        }

        Show show = new Show(
                id,
                movieId,
                theatreId,
                showTime,
                ticketPrice
        );

        showDAO.updateShow(show);
    }

    public void deleteShow(int id) {

        Show show = showDAO.getShowById(id);

        if (show == null) {
            System.out.println("Show not found.");
            return;
        }

        showDAO.deleteShow(id);
    }
}