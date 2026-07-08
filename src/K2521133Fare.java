// The four numbers that make up a ticket price, worked out once at booking time.
public class K2521133Fare {
    private final double baseFare;
    private final double bookingFee;
    private final double tax;
    private final double total;

    public K2521133Fare(double baseFare, double bookingFee, double tax, double total) {
        this.baseFare = baseFare;
        this.bookingFee = bookingFee;
        this.tax = tax;
        this.total = total;
    }

    public double getBaseFare() {
        return baseFare;
    }

    public double getBookingFee() {
        return bookingFee;
    }

    public double getTax() {
        return tax;
    }

    public double getTotal() {
        return total;
    }
}
