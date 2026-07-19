public class Student {
    private String name;
    private String studentId;
    private String route;
    private String contact;
    private String password;
    private String assignedBus;
    private int assignedSeat;

    public Student(String name, String studentId, String route, String contact, String password) {
        this.name = name;
        this.studentId = studentId;
        this.route = route;
        this.contact = contact;
        this.password = password;
        this.assignedBus = "Not Assigned";
        this.assignedSeat = -1;
    }

    public boolean authenticate(String id, String pass) {
        return this.studentId.equals(id) && this.password.equals(pass);
    }

    public String getName()         { return name; }
    public String getStudentId()    { return studentId; }
    public String getRoute()        { return route; }
    public String getContact()      { return contact; }
    public String getAssignedBus()  { return assignedBus; }
    public int getAssignedSeat()    { return assignedSeat; }

    public void setRoute(String route)           { this.route = route; }
    public void setContact(String contact)       { this.contact = contact; }
    public void setAssignedBus(String bus)       { this.assignedBus = bus; }
    public void setAssignedSeat(int seat)        { this.assignedSeat = seat; }

    public void printDetails() {
        System.out.println("┌─────────────────────────────────────┐");
        System.out.printf("│ Name    : %-26s│%n", name);
        System.out.printf("│ ID      : %-26s│%n", studentId);
        System.out.printf("│ Route   : %-26s│%n", route);
        System.out.printf("│ Contact : %-26s│%n", contact);
        System.out.printf("│ Bus     : %-26s│%n", assignedBus);
        System.out.printf("│ Seat    : %-26s│%n", assignedSeat == -1 ? "Not Assigned" : String.valueOf(assignedSeat));
        System.out.println("└─────────────────────────────────────┘");
    }

    @Override
    public String toString() {
        return String.format("%-12s %-10s %-12s %-13s %-8s %-4s",
            name, studentId, route, contact, assignedBus,
            assignedSeat == -1 ? "N/A" : String.valueOf(assignedSeat));
    }
}
