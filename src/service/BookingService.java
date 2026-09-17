package service;

import dao.BookingDAO;
import dao.CustomerDAO;
import dao.SeatDAO;
import dao.ShowDAO;
import model.Booking;
import model.Customer;
import model.Seat;
import model.Show;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class BookingService {

    private final BookingDAO bookingDAO;
    private final CustomerDAO customerDAO;
    private final ShowDAO showDAO;
    private final SeatDAO seatDAO;

    public BookingService() {
        bookingDAO = new BookingDAO();
        customerDAO = new CustomerDAO();
        showDAO = new ShowDAO();
        seatDAO = new SeatDAO();
    }

    public boolean createBooking(
            int customerId,
            int showId,
            int seatId) {

        // Check customer
        Customer customer =
                customerDAO.getCustomerById(customerId);

        if (customer == null) {
            System.out.println("Customer not found.");
            return false;
        }

        // Check show
        Show show =
                showDAO.getShowById(showId);

        if (show == null) {
            System.out.println("Show not found.");
            return false;
        }

        // Check seat
        Seat seat =
                seatDAO.getSeatById(seatId);

        if (seat == null) {
            System.out.println("Seat not found.");
            return false;
        }

        // Make sure seat belongs to the show's theatre
        if (seat.getTheatreId() != getTheatreIdForShow(show)) {
            System.out.println(
                    "This seat does not belong to the show's theatre."
            );
            return false;
        }

        // Check whether this seat is already booked
        List<Booking> bookings =
                bookingDAO.getAllBookings();

        for (Booking booking : bookings) {

            if (booking.getShowId() == showId &&
                    booking.getSeatId() == seatId) {

                System.out.println(
                        "This seat is already booked for this show."
                );

                return false;
            }
        }

        String bookingTime =
                LocalDateTime.now().format(
                        DateTimeFormatter.ofPattern(
                                "yyyy-MM-dd HH:mm:ss"
                        )
                );

        Booking booking = new Booking(
                customerId,
                showId,
                seatId,
                bookingTime,
                show.getTicketPrice()
        );

        return bookingDAO.createBooking(booking);
    }

    private int getTheatreIdForShow(Show show) {

        return show.getTheatreId();
    }

    public List<Booking> getAllBookings() {

        return bookingDAO.getAllBookings();
    }

    public Booking getBookingById(int id) {

        return bookingDAO.getBookingById(id);
    }

    public boolean cancelBooking(int id) {

        Booking booking =
                bookingDAO.getBookingById(id);

        if (booking == null) {
            System.out.println("Booking not found.");
            return false;
        }

        return bookingDAO.deleteBooking(id);
    }
}