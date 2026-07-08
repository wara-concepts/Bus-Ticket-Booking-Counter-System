import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

// Tracks which seats are taken on each route for each day. A cancelled booking
// frees its seat here so the next customer can take it.
public class K2521133SeatManager {

    private final Map<String, Set<Integer>> occupied = new HashMap<>();

    // Gives back the lowest free seat number, or -1 when the bus is full.
    public int reserveSeat(K2521133Route route, LocalDate date, int capacity) {
        Set<Integer> taken = occupied.computeIfAbsent(key(route, date), k -> new TreeSet<>());
        for (int seat = 1; seat <= capacity; seat++) {
            if (!taken.contains(seat)) {
                taken.add(seat);
                return seat;
            }
        }
        return -1;
    }

    public void releaseSeat(K2521133Route route, LocalDate date, int seat) {
        Set<Integer> taken = occupied.get(key(route, date));
        if (taken != null) {
            taken.remove(seat);
        }
    }

    private String key(K2521133Route route, LocalDate date) {
        return route.name() + "|" + date;
    }
}
