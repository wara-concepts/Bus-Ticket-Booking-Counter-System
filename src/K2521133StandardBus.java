public class K2521133StandardBus extends K2521133Bus {

    public K2521133StandardBus() {
        super(40);
    }

    @Override
    public double getBaseFare(K2521133Route route) {
        return route.getStandardFare();
    }

    @Override
    public String typeName() {
        return "Standard";
    }
}
