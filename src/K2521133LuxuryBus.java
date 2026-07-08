public class K2521133LuxuryBus extends K2521133Bus {

    public K2521133LuxuryBus() {
        super(30);
    }

    @Override
    public double getBaseFare(K2521133Route route) {
        return route.getLuxuryFare();
    }

    @Override
    public String typeName() {
        return "Luxury";
    }
}
