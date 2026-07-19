import java.util.HashMap;
import java.util.Map;

public class SeatManager {

    // Maps busNumber -> boolean array (index 1..capacity); true = occupied
    private Map<String, boolean[]> seatMap;

    public SeatManager() {
        seatMap = new HashMap<>();
    }

    public void initializeBus(String busNumber, int capacity) {
        seatMap.put(busNumber, new boolean[capacity + 1]);
    }

    public void removeBus(String busNumber) {
        seatMap.remove(busNumber);
    }

    /** Returns allocated seat number, or -1 if bus full or not found */
    public int allocateSeat(String busNumber) {
        boolean[] seats = seatMap.get(busNumber);
        if (seats == null) return -1;
        for (int i = 1; i < seats.length; i++) {
            if (!seats[i]) {
                seats[i] = true;
                return i;
            }
        }
        return -1;
    }

    public boolean deallocateSeat(String busNumber, int seatNumber) {
        boolean[] seats = seatMap.get(busNumber);
        if (seats == null || seatNumber < 1 || seatNumber >= seats.length) return false;
        if (!seats[seatNumber]) return false;
        seats[seatNumber] = false;
        return true;
    }

    public void displaySeatMatrix(String busNumber) {
        boolean[] seats = seatMap.get(busNumber);
        if (seats == null) {
            System.out.println("  Bus not found in seat records.");
            return;
        }
        System.out.println("\n  === Seat Matrix: Bus " + busNumber + " ===");
        System.out.println("  [O = Available]  [X = Occupied]");
        System.out.println();
        int cols = 5;
        for (int i = 1; i < seats.length; i++) {
            System.out.printf("  [%2d:%s]", i, seats[i] ? "X" : "O");
            if ((i % cols) == 0 || i == seats.length - 1) System.out.println();
        }
        System.out.println();
    }

    public int countAvailable(String busNumber) {
        boolean[] seats = seatMap.get(busNumber);
        if (seats == null) return 0;
        int count = 0;
        for (int i = 1; i < seats.length; i++)
            if (!seats[i]) count++;
        return count;
    }
}
