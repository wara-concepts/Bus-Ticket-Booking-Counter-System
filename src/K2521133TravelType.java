public enum K2521133TravelType {
    ONE_WAY("One-way"),
    RETURN("Return");

    private final String label;

    K2521133TravelType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
