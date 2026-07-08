// The three routes the company runs, each carrying its own standard and luxury fare.
public enum K2521133Route {
    JAFFNA("Colombo -> Jaffna", 1600.00, 2500.00),
    MATARA("Colombo -> Matara", 800.00, 1200.00),
    BADULLA("Colombo -> Badulla", 1000.00, 1800.00);

    private final String label;
    private final double standardFare;
    private final double luxuryFare;

    K2521133Route(String label, double standardFare, double luxuryFare) {
        this.label = label;
        this.standardFare = standardFare;
        this.luxuryFare = luxuryFare;
    }

    public String getLabel() {
        return label;
    }

    public double getStandardFare() {
        return standardFare;
    }

    public double getLuxuryFare() {
        return luxuryFare;
    }
}
