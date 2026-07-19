import java.util.HashMap;
import java.util.Map;

public class Admin {

    private static final String ADMIN_PASSWORD = "admin123";

    private Map<String, Student> students;
    private Map<String, Bus>     buses;
    private SeatManager          seatManager;

    public Admin() {
        students    = new HashMap<>();
        buses       = new HashMap<>();
        seatManager = new SeatManager();
        loadSampleData();
    }

    public boolean authenticate(String password) {
        return ADMIN_PASSWORD.equals(password);
    }

    // ─── Student Operations ────────────────────────────────────────────────

    public boolean registerStudent(String name, String id, String route,
                                   String contact, String password) {
        if (students.containsKey(id)) return false;
        students.put(id, new Student(name, id, route, contact, password));
        return true;
    }

    public boolean removeStudent(String id) {
        Student s = students.get(id);
        if (s == null) return false;
        if (!s.getAssignedBus().equals("Not Assigned")) {
            Bus bus = buses.get(s.getAssignedBus());
            if (bus != null) {
                bus.removeStudent(id);
                seatManager.deallocateSeat(s.getAssignedBus(), s.getAssignedSeat());
            }
        }
        students.remove(id);
        return true;
    }

    public Student loginStudent(String id, String password) {
        Student s = students.get(id);
        return (s != null && s.authenticate(id, password)) ? s : null;
    }

    // ─── Bus Operations ────────────────────────────────────────────────────

    public boolean addBus(String busNumber, String route, int capacity) {
        if (buses.containsKey(busNumber)) return false;
        buses.put(busNumber, new Bus(busNumber, route, capacity));
        seatManager.initializeBus(busNumber, capacity);
        return true;
    }

    public boolean removeBus(String busNumber) {
        Bus bus = buses.get(busNumber);
        if (bus == null) return false;
        for (String sid : bus.getAssignedStudentIds()) {
            Student s = students.get(sid);
            if (s != null) { s.setAssignedBus("Not Assigned"); s.setAssignedSeat(-1); }
        }
        seatManager.removeBus(busNumber);
        buses.remove(busNumber);
        return true;
    }

    // ─── Seat Assignment ───────────────────────────────────────────────────

    public String assignStudentToBus(String studentId, String busNumber) {
        Student student = students.get(studentId);
        Bus bus         = buses.get(busNumber);
        if (student == null)                                      return "  ✘ Student not found.";
        if (bus == null)                                          return "  ✘ Bus not found.";
        if (!bus.getRoute().equalsIgnoreCase(student.getRoute())) return "  ✘ Route mismatch (Student: "
                                                                        + student.getRoute() + ", Bus: " + bus.getRoute() + ")";
        if (!student.getAssignedBus().equals("Not Assigned"))     return "  ✘ Student already assigned to " + student.getAssignedBus();
        int seat = seatManager.allocateSeat(busNumber);
        if (seat == -1)                                           return "  ✘ No seats available on " + busNumber;
        bus.addStudent(studentId);
        student.setAssignedBus(busNumber);
        student.setAssignedSeat(seat);
        return "  ✔ Seat " + seat + " assigned to " + student.getName() + " on Bus " + busNumber;
    }

    // ─── Reports ───────────────────────────────────────────────────────────

    public void viewAllBuses() {
        System.out.println("\n  ╔══════════════════════════════════════════════════════════╗");
        System.out.println("  ║                    REGISTERED BUSES                      ║");
        System.out.println("  ╚══════════════════════════════════════════════════════════╝");
        if (buses.isEmpty()) { System.out.println("  No buses registered."); return; }
        for (Bus b : buses.values()) System.out.println("  " + b);
    }

    public void viewAllocationReport() {
        System.out.println("\n  ╔══════════════════════════════════════════════════════════════╗");
        System.out.println("  ║                    FULL ALLOCATION REPORT                    ║");
        System.out.println("  ╚══════════════════════════════════════════════════════════════╝");
        System.out.printf("  %-12s %-10s %-12s %-13s %-8s %-4s%n",
            "Name", "ID", "Route", "Contact", "Bus", "Seat");
        System.out.println("  " + "─".repeat(62));
        if (students.isEmpty()) { System.out.println("  No students registered."); return; }
        for (Student s : students.values()) System.out.println("  " + s);
        System.out.println();
    }

    public Map<String, Student> getStudents() { return students; }
    public Map<String, Bus>     getBuses()    { return buses; }
    public SeatManager          getSeatManager() { return seatManager; }

    // ─── Sample Data ───────────────────────────────────────────────────────

    private void loadSampleData() {
        addBus("BUS01", "Route-A", 10);
        addBus("BUS02", "Route-B", 8);
        addBus("BUS03", "Route-A", 6);

        registerStudent("Alice",   "S001", "Route-A", "9900001111", "pass1");
        registerStudent("Bob",     "S002", "Route-B", "9900002222", "pass2");
        registerStudent("Charlie", "S003", "Route-A", "9900003333", "pass3");
        registerStudent("Diana",   "S004", "Route-B", "9900004444", "pass4");
        registerStudent("Ethan",   "S005", "Route-A", "9900005555", "pass5");

        assignStudentToBus("S001", "BUS01");
        assignStudentToBus("S003", "BUS01");
        assignStudentToBus("S005", "BUS01");
        assignStudentToBus("S002", "BUS02");
        assignStudentToBus("S004", "BUS02");
    }
}
