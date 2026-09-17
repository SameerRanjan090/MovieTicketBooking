package service;

import dao.TheatreDAO;
import model.Theatre;

import java.util.List;

public class TheatreService {

    private final TheatreDAO theatreDAO;
    private final SeatService seatService;

    public TheatreService() {
        theatreDAO = new TheatreDAO();
        seatService = new SeatService();
    }

    public void addTheatre(String name, String location, int totalSeats) {

        if (name == null || name.isBlank()) {
            System.out.println("Theatre name cannot be empty.");
            return;
        }

        if (location == null || location.isBlank()) {
            System.out.println("Location cannot be empty.");
            return;
        }

        if (totalSeats <= 0) {
            System.out.println("Total seats must be greater than 0.");
            return;
        }

        Theatre theatre = new Theatre(
                name,
                location,
                totalSeats
        );

        theatreDAO.addTheatre(theatre);

        // Find the newly created theatre and generate its seats
        List<Theatre> theatres = theatreDAO.getAllTheatres();

        Theatre latestTheatre = theatres.get(theatres.size() - 1);

        seatService.generateSeats(latestTheatre.getId());
    }

    public List<Theatre> getAllTheatres() {
        return theatreDAO.getAllTheatres();
    }

    public Theatre getTheatreById(int id) {
        return theatreDAO.getTheatreById(id);
    }

    public void updateTheatre(
            int id,
            String name,
            String location,
            int totalSeats) {

        Theatre existingTheatre =
                theatreDAO.getTheatreById(id);

        if (existingTheatre == null) {
            System.out.println("Theatre not found.");
            return;
        }

        if (name == null || name.isBlank()) {
            System.out.println("Theatre name cannot be empty.");
            return;
        }

        if (location == null || location.isBlank()) {
            System.out.println("Location cannot be empty.");
            return;
        }

        if (totalSeats <= 0) {
            System.out.println("Total seats must be greater than 0.");
            return;
        }

        Theatre theatre = new Theatre(
                id,
                name,
                location,
                totalSeats
        );

        theatreDAO.updateTheatre(theatre);
    }

    public void deleteTheatre(int id) {

        Theatre theatre =
                theatreDAO.getTheatreById(id);

        if (theatre == null) {
            System.out.println("Theatre not found.");
            return;
        }

        theatreDAO.deleteTheatre(id);
    }
}