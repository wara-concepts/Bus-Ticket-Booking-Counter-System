import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

// The counter screen. It drives the menu, collects details and hands the work
// to the service, then reports back what happened.
public class K2521133BookingConsole {

    private final K2521133BookingService service = new K2521133BookingService();
    private final K2521133TicketPrinter printer = new K2521133TicketPrinter();
    private final K2521133InputReader input;

    public K2521133BookingConsole(Scanner scanner) {
        this.input = new K2521133InputReader(scanner);
    }

    public void run() {
        System.out.println("=======================================");
        System.out.println("   Bus Ticket Booking Counter System");
        System.out.println("=======================================");

        boolean running = true;
        while (running) {
            printMenu();
            int choice = input.readInt("Select an option: ", 0, 5);
            System.out.println();
            switch (choice) {
                case 1 -> createBooking();
                case 2 -> updateBooking();
                case 3 -> deleteBooking();
                case 4 -> searchBooking();
                case 5 -> printTicket();
                case 0 -> running = false;
            }
            System.out.println();
        }
        System.out.println("Counter closed. Have a good day.");
    }

    private void printMenu() {
        System.out.println("1. Create booking");
        System.out.println("2. Update booking");
        System.out.println("3. Delete booking");
        System.out.println("4. Search booking");
        System.out.println("5. Print ticket");
        System.out.println("0. Exit");
    }

    private void createBooking() {
        System.out.println("--- Create booking ---");
        String nic = input.readText("NIC/Passport: ");
        String name = input.readText("Name: ");
        String contact = input.readText("Contact: ");
        K2521133Customer customer = new K2521133Customer(nic, name, contact);

        K2521133Route route = pickRoute();
        LocalDate date = input.readDate("Travel date (YYYY-MM-DD): ");
        K2521133Bus bus = pickBus();
        K2521133TravelType travelType = pickTravelType();

        try {
            K2521133Booking booking = service.createBooking(customer, route, date, bus, travelType);
            System.out.println("Booking confirmed. Id: " + booking.getId()
                    + ", seat No. " + booking.getSeatNumber());
        } catch (IllegalStateException e) {
            System.out.println("Could not book: " + e.getMessage());
        }
    }

    private void updateBooking() {
        System.out.println("--- Update booking ---");
        K2521133Booking booking = requireBooking();
        if (booking == null) {
            return;
        }

        System.out.println("Leave the reservation as is, or enter new values.");
        booking.getCustomer().setName(input.readText("Name [" + booking.getCustomer().getName() + "]: "));
        booking.getCustomer().setContact(
                input.readText("Contact [" + booking.getCustomer().getContact() + "]: "));

        K2521133Route route = pickRoute();
        LocalDate date = input.readDate("Travel date (YYYY-MM-DD): ");
        K2521133Bus bus = pickBus();
        K2521133TravelType travelType = pickTravelType();

        try {
            service.updateReservation(booking.getId(), route, date, bus, travelType);
            System.out.println("Booking " + booking.getId() + " updated. Seat No. "
                    + booking.getSeatNumber());
        } catch (IllegalStateException e) {
            System.out.println("Could not update: " + e.getMessage());
        }
    }

    private void deleteBooking() {
        System.out.println("--- Delete booking ---");
        String id = input.readText("Booking id: ");
        if (service.deleteBooking(id)) {
            System.out.println("Booking " + id + " cancelled and seat freed.");
        } else {
            System.out.println("No booking found with id " + id + ".");
        }
    }

    private void searchBooking() {
        System.out.println("--- Search booking ---");
        System.out.println("1. By NIC/Passport");
        System.out.println("2. By destination");
        int choice = input.readInt("Search by: ", 1, 2);

        List<K2521133Booking> results;
        if (choice == 1) {
            results = service.searchByNic(input.readText("NIC/Passport: "));
        } else {
            results = service.filterByDestination(pickRoute());
        }

        if (results.isEmpty()) {
            System.out.println("No matching bookings.");
            return;
        }
        for (K2521133Booking booking : results) {
            System.out.printf("%s  %-8s  %-16s  %s  seat %d%n",
                    booking.getId(), booking.getCustomer().getNic(), booking.getRoute().getLabel(),
                    booking.getTravelDate(), booking.getSeatNumber());
        }
    }

    private void printTicket() {
        System.out.println("--- Print ticket ---");
        K2521133Booking booking = requireBooking();
        if (booking != null) {
            System.out.println();
            System.out.print(printer.format(booking));
        }
    }

    private K2521133Booking requireBooking() {
        String id = input.readText("Booking id: ");
        K2521133Booking booking = service.find(id);
        if (booking == null) {
            System.out.println("No booking found with id " + id + ".");
        }
        return booking;
    }

    private K2521133Route pickRoute() {
        System.out.println("Destinations: 1. Jaffna  2. Matara  3. Badulla");
        int choice = input.readInt("Destination: ", 1, 3);
        return switch (choice) {
            case 1 -> K2521133Route.JAFFNA;
            case 2 -> K2521133Route.MATARA;
            default -> K2521133Route.BADULLA;
        };
    }

    private K2521133Bus pickBus() {
        System.out.println("Bus type: 1. Standard  2. Luxury");
        int choice = input.readInt("Bus type: ", 1, 2);
        return choice == 1 ? new K2521133StandardBus() : new K2521133LuxuryBus();
    }

    private K2521133TravelType pickTravelType() {
        System.out.println("Travel type: 1. One-way  2. Return");
        int choice = input.readInt("Travel type: ", 1, 2);
        return choice == 1 ? K2521133TravelType.ONE_WAY : K2521133TravelType.RETURN;
    }
}
