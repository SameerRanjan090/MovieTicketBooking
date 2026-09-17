package model;

public class Booking {

    private int id;
    private int customerId;
    private int showId;
    private int seatId;
    private String bookingTime;
    private double totalPrice;

    public Booking(
            int customerId,
            int showId,
            int seatId,
            String bookingTime,
            double totalPrice) {

        this.customerId = customerId;
        this.showId = showId;
        this.seatId = seatId;
        this.bookingTime = bookingTime;
        this.totalPrice = totalPrice;
    }

    public Booking(
            int id,
            int customerId,
            int showId,
            int seatId,
            String bookingTime,
            double totalPrice) {

        this.id = id;
        this.customerId = customerId;
        this.showId = showId;
        this.seatId = seatId;
        this.bookingTime = bookingTime;
        this.totalPrice = totalPrice;
    }

    public int getId() {
        return id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getShowId() {
        return showId;
    }

    public int getSeatId() {
        return seatId;
    }

    public String getBookingTime() {
        return bookingTime;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setShowId(int showId) {
        this.showId = showId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public void setBookingTime(String bookingTime) {
        this.bookingTime = bookingTime;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {

        return "Booking{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", showId=" + showId +
                ", seatId=" + seatId +
                ", bookingTime='" + bookingTime + '\'' +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
