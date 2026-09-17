package model;

public class Show {

    private int id;
    private int movieId;
    private int theatreId;
    private String showTime;
    private double ticketPrice;

    public Show(int movieId, int theatreId, String showTime, double ticketPrice) {
        this.movieId = movieId;
        this.theatreId = theatreId;
        this.showTime = showTime;
        this.ticketPrice = ticketPrice;
    }

    public Show(int id, int movieId, int theatreId,
                String showTime, double ticketPrice) {
        this.id = id;
        this.movieId = movieId;
        this.theatreId = theatreId;
        this.showTime = showTime;
        this.ticketPrice = ticketPrice;
    }

    public int getId() {
        return id;
    }

    public int getMovieId() {
        return movieId;
    }

    public int getTheatreId() {
        return theatreId;
    }

    public String getShowTime() {
        return showTime;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public void setTheatreId(int theatreId) {
        this.theatreId = theatreId;
    }

    public void setShowTime(String showTime) {
        this.showTime = showTime;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    @Override
    public String toString() {
        return "Show{" +
                "id=" + id +
                ", movieId=" + movieId +
                ", theatreId=" + theatreId +
                ", showTime='" + showTime + '\'' +
                ", ticketPrice=" + ticketPrice +
                '}';
    }
}