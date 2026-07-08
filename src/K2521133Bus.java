// Base class for the two bus types. Each subclass knows which of the route's
// fares applies to it, so the fare calculator can stay unaware of the type.
public abstract class K2521133Bus {
    private final int capacity;

    protected K2521133Bus(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public abstract double getBaseFare(K2521133Route route);

    public abstract String typeName();
}
