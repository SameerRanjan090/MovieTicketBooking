package service;

import dao.SeatDAO;
import dao.TheatreDAO;
import model.Seat;
import model.Theatre;

import java.util.List;

public class SeatService {

    private final SeatDAO seatDAO;
    private final TheatreDAO theatreDAO;

    public SeatService() {
        seatDAO = new SeatDAO();
        theatreDAO = new TheatreDAO();
    }

    public void generateSeats(int theatreId) {

        Theatre theatre = theatreDAO.getTheatreById(theatreId);

        if (theatre == null) {
            System.out.println("Theatre not found.");
            return;
        }

        int totalSeats = theatre.getTotalSeats();

        int seatsPerRow = 10;

        for (int i = 0; i < totalSeats; i++) {

            int rowNumber = i / seatsPerRow;
            int seatPosition = (i % seatsPerRow) + 1;

            char row = (char) ('A' + rowNumber);

            String seatNumber = row + String.valueOf(seatPosition);

            if (!seatDAO.seatExists(theatreId, seatNumber)) {

                Seat seat = new Seat(
                        theatreId,
                        seatNumber,
                        true
                );

                seatDAO.addSeat(seat);
            }
        }

        System.out.println("Seats generated successfully.");
    }

    public List<Seat> getSeatsByTheatre(int theatreId) {

        return seatDAO.getSeatsByTheatre(theatreId);
    }

    public Seat getSeatById(int id) {

        return seatDAO.getSeatById(id);
    }

    public boolean isSeatAvailable(int seatId) {

        Seat seat = seatDAO.getSeatById(seatId);

        return seat != null && seat.isAvailable();
    }

    public void setSeatAvailability(int seatId, boolean available) {

        Seat seat = seatDAO.getSeatById(seatId);

        if (seat == null) {
            System.out.println("Seat not found.");
            return;
        }

        seatDAO.updateAvailability(seatId, available);
    }
}