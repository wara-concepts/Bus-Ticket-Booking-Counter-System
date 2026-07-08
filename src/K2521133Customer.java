// Customer identified by NIC or passport. That id is what stops a person
// double-booking the same route on the same day.
public class K2521133Customer {
    private final String nic;
    private String name;
    private String contact;

    public K2521133Customer(String nic, String name, String contact) {
        this.nic = nic;
        this.name = name;
        this.contact = contact;
    }

    public String getNic() {
        return nic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
