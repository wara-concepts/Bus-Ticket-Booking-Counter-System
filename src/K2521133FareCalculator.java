// Turns a bus + route + travel type into the priced-out fare, following the
// company's rules: 3.5% booking fee, 1.5% tax, and for return trips double the
// base fare then knock 5% off before the fee and tax are added.
public class K2521133FareCalculator {

    private static final double BOOKING_FEE_RATE = 0.035;
    private static final double TAX_RATE = 0.015;
    private static final double RETURN_DISCOUNT_RATE = 0.05;

    public K2521133Fare calculate(K2521133Bus bus, K2521133Route route, K2521133TravelType travelType) {
        double base = bus.getBaseFare(route);

        if (travelType == K2521133TravelType.RETURN) {
            base = base * 2 * (1 - RETURN_DISCOUNT_RATE);
        }

        double bookingFee = base * BOOKING_FEE_RATE;
        double tax = (base + bookingFee) * TAX_RATE;
        double total = base + bookingFee + tax;

        return new K2521133Fare(base, bookingFee, tax, total);
    }
}
