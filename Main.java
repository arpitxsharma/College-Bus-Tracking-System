import java.util.Scanner;

public class Main {

    private static final Scanner sc      = new Scanner(System.in);
    private static final Admin   admin   = new Admin();
    private static final TrackingSimulator tracker = new TrackingSimulator(admin.getBuses());

    public static void main(String[] args) {
        printBanner();
        while (true) {
            printMenu("MAIN MENU",
                "1. Admin Login",
                "2. Student Login",
                "3. Register as New Student",
                "4. View Live Bus Tracking",
                "0. Exit");
            switch (readInt()) {
                case 1: adminMenu();           break;
                case 2: studentMenu();         break;
                case 3: registerStudentFlow(); break;
                case 4: tracker.showAllStatus(); break;
                case 0:
                    System.out.println("\n  Goodbye! 👋\n");
                    sc.close();
                    return;
                default: System.out.println("  Invalid choice. Try again.");
            }
        }
    }

    // ─── Admin Flow ────────────────────────────────────────────────────────

    private static void adminMenu() {
        System.out.print("\n  Enter Admin Password: ");
        if (!admin.authenticate(sc.nextLine().trim())) {
            System.out.println("  ✘ Authentication failed.");
            return;
        }
        System.out.println("  ✔ Admin logged in.\n");

        while (true) {
            printMenu("ADMIN MENU",
                "1.  Add Bus",
                "2.  Remove Bus",
                "3.  View All Buses",
                "4.  Register Student",
                "5.  Remove Student",
                "6.  Assign Student to Bus",
                "7.  View Allocation Report",
                "8.  View Seat Matrix",
                "9.  Simulate Bus Status",
                "10. Live Tracking View",
                "0.  Logout");
            switch (readInt()) {
                case 1:  addBusFlow();        break;
                case 2:  removeBusFlow();     break;
                case 3:  admin.viewAllBuses(); break;
                case 4:  registerStudentFlow(); break;
                case 5:  removeStudentFlow(); break;
                case 6:  assignStudentFlow(); break;
                case 7:  admin.viewAllocationReport(); break;
                case 8:  seatMatrixFlow();    break;
                case 9:  advanceStatusFlow(); break;
                case 10: tracker.showAllStatus(); break;
                case 0:
                    System.out.println("  Logged out.");
                    return;
                default: System.out.println("  Invalid choice.");
            }
        }
    }

    private static void addBusFlow() {
        System.out.print("  Bus Number  : "); String num   = sc.nextLine().trim();
        System.out.print("  Route       : "); String route = sc.nextLine().trim();
        System.out.print("  Capacity    : "); int cap = readInt();
        if (cap <= 0) { System.out.println("  ✘ Invalid capacity."); return; }
        System.out.println(admin.addBus(num, route, cap)
            ? "  ✔ Bus " + num + " added successfully."
            : "  ✘ Bus number already exists.");
    }

    private static void removeBusFlow() {
        System.out.print("  Bus Number to remove: ");
        String num = sc.nextLine().trim();
        System.out.println(admin.removeBus(num)
            ? "  ✔ Bus " + num + " removed. Affected students unassigned."
            : "  ✘ Bus not found.");
    }

    private static void removeStudentFlow() {
        System.out.print("  Student ID to remove: ");
        String id = sc.nextLine().trim();
        System.out.println(admin.removeStudent(id)
            ? "  ✔ Student " + id + " removed."
            : "  ✘ Student not found.");
    }

    private static void assignStudentFlow() {
        System.out.print("  Student ID  : "); String sid = sc.nextLine().trim();
        System.out.print("  Bus Number  : "); String bus = sc.nextLine().trim();
        System.out.println(admin.assignStudentToBus(sid, bus));
    }

    private static void seatMatrixFlow() {
        System.out.print("  Bus Number: ");
        String bus = sc.nextLine().trim();
        admin.getSeatManager().displaySeatMatrix(bus);
    }

    private static void advanceStatusFlow() {
        System.out.print("  Bus Number: ");
        String bus = sc.nextLine().trim();
        tracker.advanceBusStatus(bus);
    }

    // ─── Student Flow ──────────────────────────────────────────────────────

    private static void studentMenu() {
        System.out.print("\n  Student ID : "); String id   = sc.nextLine().trim();
        System.out.print("  Password   : "); String pass = sc.nextLine().trim();
        Student student = admin.loginStudent(id, pass);
        if (student == null) { System.out.println("  ✘ Invalid credentials."); return; }
        System.out.println("\n  ✔ Welcome, " + student.getName() + "!");

        while (true) {
            printMenu("STUDENT MENU",
                "1. View My Bus & Seat Details",
                "2. Update Transport Preferences",
                "3. View Live Bus Tracking",
                "4. View Seat Matrix of My Bus",
                "0. Logout");
            switch (readInt()) {
                case 1: student.printDetails(); break;
                case 2: updatePreferencesFlow(student); break;
                case 3: tracker.showAllStatus(); break;
                case 4:
                    if (student.getAssignedBus().equals("Not Assigned"))
                        System.out.println("  You are not yet assigned to a bus. Contact admin.");
                    else
                        admin.getSeatManager().displaySeatMatrix(student.getAssignedBus());
                    break;
                case 0: System.out.println("  Logged out."); return;
                default: System.out.println("  Invalid choice.");
            }
        }
    }

    private static void updatePreferencesFlow(Student s) {
        System.out.print("  New Route   [" + s.getRoute()   + "] (Enter to skip): ");
        String route = sc.nextLine().trim();
        System.out.print("  New Contact [" + s.getContact() + "] (Enter to skip): ");
        String contact = sc.nextLine().trim();
        if (!route.isEmpty())   s.setRoute(route);
        if (!contact.isEmpty()) s.setContact(contact);
        System.out.println("  ✔ Preferences updated successfully.");
    }

    private static void registerStudentFlow() {
        System.out.println("\n  === Student Registration ===");
        System.out.print("  Full Name   : "); String name    = sc.nextLine().trim();
        System.out.print("  Student ID  : "); String id      = sc.nextLine().trim();
        System.out.print("  Route       : "); String route   = sc.nextLine().trim();
        System.out.print("  Contact No. : "); String contact = sc.nextLine().trim();
        System.out.print("  Password    : "); String pass    = sc.nextLine().trim();

        if (name.isEmpty() || id.isEmpty() || route.isEmpty()) {
            System.out.println("  ✘ Name, ID and Route are required.");
            return;
        }
        System.out.println(admin.registerStudent(name, id, route, contact, pass)
            ? "  ✔ Registration successful! Contact admin for bus assignment."
            : "  ✘ Student ID already exists.");
    }

    // ─── Helpers ───────────────────────────────────────────────────────────

    private static void printBanner() {
        System.out.println();
        System.out.println("  ╔═══════════════════════════════════════════════╗");
        System.out.println("  ║   College Bus Tracking & Seat Management      ║");
        System.out.println("  ║              System  v1.0                     ║");
        System.out.println("  ╠═══════════════════════════════════════════════╣");
        System.out.println("  ║  Sample Logins:                               ║");
        System.out.println("  ║  Admin    → Password : admin123               ║");
        System.out.println("  ║  Student  → ID: S001  Password: pass1         ║");
        System.out.println("  ╚═══════════════════════════════════════════════╝");
    }

    private static void printMenu(String title, String... options) {
        System.out.println("\n  ┌─── " + title + " " + "─".repeat(Math.max(0, 28 - title.length())) + "┐");
        for (String opt : options) System.out.println("  │  " + opt);
        System.out.println("  └" + "─".repeat(34) + "┘");
        System.out.print("  Choice: ");
    }

    private static int readInt() {
        try { return Integer.parseInt(sc.nextLine().trim()); }
        catch (NumberFormatException e) { return -1; }
    }
}
