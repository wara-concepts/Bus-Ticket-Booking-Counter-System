import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// The heart of the counter system. Holds every booking, applies the fare and
// seat rules, and enforces the one-seat-per-customer-per-route-per-day rule.
public class K2521133BookingService {

    private final Map<String, K2521133Booking> bookings = new LinkedHashMap<>();
    private final K2521133SeatManager seatManager = new K2521133SeatManager();
    private final K2521133FareCalculator fareCalculator = new K2521133FareCalculator();
    private int nextId = 1;

    public K2521133Booking createBooking(K2521133Customer customer, K2521133Route route,
                                         LocalDate date, K2521133Bus bus, K2521133TravelType travelType) {
        if (hasClash(customer.getNic(), route, date, null)) {
            throw new IllegalStateException(
                    customer.getNic() + " already has a booking on this route for " + date);
        }

        int seat = seatManager.reserveSeat(route, date, bus.getCapacity());
        if (seat == -1) {
            throw new IllegalStateException("No seats left on this bus for " + date);
        }

        String id = String.format("BK%03d", nextId++);
        K2521133Booking booking = new K2521133Booking(id, customer, route, date, bus, travelType);
        booking.setSeatNumber(seat);
        booking.setFare(fareCalculator.calculate(bus, route, travelType));
        bookings.put(id, booking);
        return booking;
    }

    public K2521133Booking find(String id) {
        return bookings.get(id);
    }

    // Changing route, date or bus means the old seat is freed and a new one taken,
    // so the clash check runs again before anything is committed.
    public void updateReservation(String id, K2521133Route route, LocalDate date,
                                  K2521133Bus bus, K2521133TravelType travelType) {
        K2521133Booking booking = require(id);

        if (hasClash(booking.getCustomer().getNic(), route, date, id)) {
            throw new IllegalStateException("That customer already travels this route on " + date);
        }

        int seat = seatManager.reserveSeat(route, date, bus.getCapacity());
        if (seat == -1) {
            throw new IllegalStateException("No seats left on this bus for " + date);
        }

        seatManager.releaseSeat(booking.getRoute(), booking.getTravelDate(), booking.getSeatNumber());
        booking.setRoute(route);
        booking.setTravelDate(date);
        booking.setBus(bus);
        booking.setTravelType(travelType);
        booking.setSeatNumber(seat);
        booking.setFare(fareCalculator.calculate(bus, route, travelType));
    }

    public boolean deleteBooking(String id) {
        K2521133Booking booking = bookings.remove(id);
        if (booking == null) {
            return false;
        }
        seatManager.releaseSeat(booking.getRoute(), booking.getTravelDate(), booking.getSeatNumber());
        return true;
    }

    public List<K2521133Booking> searchByNic(String nic) {
        List<K2521133Booking> matches = new ArrayList<>();
        for (K2521133Booking booking : bookings.values()) {
            if (booking.getCustomer().getNic().equalsIgnoreCase(nic)) {
                matches.add(booking);
            }
        }
        return matches;
    }

    public List<K2521133Booking> filterByDestination(K2521133Route route) {
        List<K2521133Booking> matches = new ArrayList<>();
        for (K2521133Booking booking : bookings.values()) {
            if (booking.getRoute() == route) {
                matches.add(booking);
            }
        }
        return matches;
    }

    public List<K2521133Booking> allBookings() {
        return new ArrayList<>(bookings.values());
    }

    private K2521133Booking require(String id) {
        K2521133Booking booking = bookings.get(id);
        if (booking == null) {
            throw new IllegalArgumentException("No booking with id " + id);
        }
        return booking;
    }

    // A clash is the same NIC on the same route and day. When updating, the
    // booking being edited is skipped so it never conflicts with itself.
    private boolean hasClash(String nic, K2521133Route route, LocalDate date, String ignoreId) {
        for (K2521133Booking booking : bookings.values()) {
            if (ignoreId != null && booking.getId().equals(ignoreId)) {
                continue;
            }
            if (booking.getCustomer().getNic().equalsIgnoreCase(nic)
                    && booking.getRoute() == route
                    && booking.getTravelDate().equals(date)) {
                return true;
            }
        }
        return false;
    }
}
