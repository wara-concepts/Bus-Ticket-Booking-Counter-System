// Formats a booking into the printed ticket the customer receives.
public class K2521133TicketPrinter {

    public String format(K2521133Booking booking) {
        K2521133Fare fare = booking.getFare();
        StringBuilder ticket = new StringBuilder();

        ticket.append("---------------------------------------\n");
        ticket.append("            BUS TICKET\n");
        ticket.append("---------------------------------------\n");
        ticket.append(line("Booking id", booking.getId()));
        ticket.append(line("Passenger", booking.getCustomer().getName()));
        ticket.append(line("NIC/Passport", booking.getCustomer().getNic()));
        ticket.append(line("Contact", booking.getCustomer().getContact()));
        ticket.append(line("Travel date", booking.getTravelDate().toString()));
        ticket.append(line("Destination", booking.getRoute().getLabel()));
        ticket.append(line("Bus type", booking.getBus().typeName()));
        ticket.append(line("Travel type", booking.getTravelType().getLabel()));
        ticket.append(line("Seat", "No. " + booking.getSeatNumber()));
        ticket.append("---------------------------------------\n");
        ticket.append(money("Base fare", fare.getBaseFare()));
        ticket.append(money("Booking fee (3.5%)", fare.getBookingFee()));
        ticket.append(money("Tax and VAT (1.5%)", fare.getTax()));
        ticket.append(money("Total", fare.getTotal()));
        ticket.append("---------------------------------------\n");

        return ticket.toString();
    }

    private String line(String label, String value) {
        return String.format("%-20s: %s%n", label, value);
    }

    private String money(String label, double amount) {
        return String.format("%-20s: LKR %,.2f%n", label, amount);
    }
}
