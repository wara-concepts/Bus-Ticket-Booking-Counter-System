import java.time.LocalDate;

public class K2521133Booking {
    private final String id;
    private final K2521133Customer customer;
    private K2521133Route route;
    private LocalDate travelDate;
    private K2521133Bus bus;
    private K2521133TravelType travelType;
    private int seatNumber;
    private K2521133Fare fare;

    public K2521133Booking(String id, K2521133Customer customer, K2521133Route route,
                           LocalDate travelDate, K2521133Bus bus, K2521133TravelType travelType) {
        this.id = id;
        this.customer = customer;
        this.route = route;
        this.travelDate = travelDate;
        this.bus = bus;
        this.travelType = travelType;
    }

    public String getId() {
        return id;
    }

    public K2521133Customer getCustomer() {
        return customer;
    }

    public K2521133Route getRoute() {
        return route;
    }

    public void setRoute(K2521133Route route) {
        this.route = route;
    }

    public LocalDate getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(LocalDate travelDate) {
        this.travelDate = travelDate;
    }

    public K2521133Bus getBus() {
        return bus;
    }

    public void setBus(K2521133Bus bus) {
        this.bus = bus;
    }

    public K2521133TravelType getTravelType() {
        return travelType;
    }

    public void setTravelType(K2521133TravelType travelType) {
        this.travelType = travelType;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public K2521133Fare getFare() {
        return fare;
    }

    public void setFare(K2521133Fare fare) {
        this.fare = fare;
    }
}
