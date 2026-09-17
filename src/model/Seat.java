package model;

public class Seat {

    private int id;
    private int theatreId;
    private String seatNumber;
    private boolean available;

    public Seat(int theatreId, String seatNumber, boolean available) {
        this.theatreId = theatreId;
        this.seatNumber = seatNumber;
        this.available = available;
    }

    public Seat(int id, int theatreId, String seatNumber, boolean available) {
        this.id = id;
        this.theatreId = theatreId;
        this.seatNumber = seatNumber;
        this.available = available;
    }

    public int getId() {
        return id;
    }

    public int getTheatreId() {
        return theatreId;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTheatreId(int theatreId) {
        this.theatreId = theatreId;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "Seat{" +
                "id=" + id +
                ", theatreId=" + theatreId +
                ", seatNumber='" + seatNumber + '\'' +
                ", available=" + available +
                '}';
    }
}