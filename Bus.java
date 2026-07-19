import java.util.ArrayList;
import java.util.List;

public class Bus {
    private String busNumber;
    private String route;
    private int capacity;
    private String status;
    private List<String> assignedStudentIds;

    private static final String[] STATUS_CYCLE = { "Scheduled", "Arriving", "Departed", "Reached" };
    private int statusIndex = 0;

    public Bus(String busNumber, String route, int capacity) {
        this.busNumber = busNumber;
        this.route = route;
        this.capacity = capacity;
        this.status = STATUS_CYCLE[0];
        this.assignedStudentIds = new ArrayList<>();
    }

    public boolean addStudent(String studentId) {
        if (isFull() || assignedStudentIds.contains(studentId)) return false;
        assignedStudentIds.add(studentId);
        return true;
    }

    public boolean removeStudent(String studentId) {
        return assignedStudentIds.remove(studentId);
    }

    public void advanceStatus() {
        statusIndex = (statusIndex + 1) % STATUS_CYCLE.length;
        status = STATUS_CYCLE[statusIndex];
    }

    public boolean isFull()              { return assignedStudentIds.size() >= capacity; }
    public int getAvailableSeats()       { return capacity - assignedStudentIds.size(); }
    public String getBusNumber()         { return busNumber; }
    public String getRoute()             { return route; }
    public int getCapacity()             { return capacity; }
    public String getStatus()            { return status; }
    public List<String> getAssignedStudentIds() { return assignedStudentIds; }

    @Override
    public String toString() {
        return String.format("Bus %-6s | Route: %-12s | Capacity: %2d | Available: %2d | Status: %-10s",
            busNumber, route, capacity, getAvailableSeats(), status);
    }
}
