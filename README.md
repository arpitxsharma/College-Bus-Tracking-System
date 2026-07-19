# College Bus Tracking & Seat Management System
### Java Mini Project | Core Java | OOP | Console Application

---

## Project Structure

```
CollegeBusSystem/
├── Student.java          → Student entity: auth, preferences, details
├── Bus.java              → Bus entity: status cycle, seat tracking
├── SeatManager.java      → Seat allocation & matrix display
├── TrackingSimulator.java→ Bus status simulation (live tracking)
├── Admin.java            → Central data store + all admin operations
└── Main.java             → Driver class: all menus & user interaction
```

**Total Lines:** ~556 | **Paradigm:** Core Java + OOP (no frameworks)

---

## OOP Concepts Used

| Concept          | Where Applied                                      |
|------------------|----------------------------------------------------|
| Encapsulation    | All classes — private fields with getters/setters  |
| Abstraction      | SeatManager hides seat logic from other classes    |
| Constructors     | Every class uses parameterized constructors        |
| Method Overriding| `toString()` in Student and Bus                   |
| Collections      | `HashMap`, `ArrayList` throughout                  |
| Exception Handling| `readInt()` in Main catches NumberFormatException |

---

## How to Run

### Step 1 — Compile all files
```bash
cd CollegeBusSystem
javac *.java
```

### Step 2 — Run the application
```bash
java Main
```

> **Requires:** Java 8 or higher. No external libraries needed.

---

## Pre-loaded Sample Data (for testing / viva)

| Type    | Data                                     |
|---------|------------------------------------------|
| Admin   | Password: `admin123`                     |
| Bus     | BUS01 (Route-A, cap 10), BUS02 (Route-B, cap 8), BUS03 (Route-A, cap 6) |
| Students | S001 Alice (Route-A), S002 Bob (Route-B), S003 Charlie (Route-A), S004 Diana (Route-B), S005 Ethan (Route-A) |
| Assigned | S001→BUS01(Seat1), S003→BUS01(Seat2), S005→BUS01(Seat3), S002→BUS02(Seat1), S004→BUS02(Seat2) |

---

## Sample Output

```
  ╔═══════════════════════════════════════════════╗
  ║   College Bus Tracking & Seat Management      ║
  ║              System  v1.0                     ║
  ╠═══════════════════════════════════════════════╣
  ║  Sample Logins:                               ║
  ║  Admin    → Password : admin123               ║
  ║  Student  → ID: S001  Password: pass1         ║
  ╚═══════════════════════════════════════════════╝

  ┌─── MAIN MENU ──────────────────────┐
  │  1. Admin Login
  │  2. Student Login
  │  3. Register as New Student
  │  4. View Live Bus Tracking
  │  0. Exit
  └──────────────────────────────────┘
  Choice: 4

  ╔═══════════════════════════════════════════╗
  ║          LIVE BUS TRACKING STATUS          ║
  ╚═══════════════════════════════════════════╝
  ⚪  Bus BUS01  | Route-A       | Scheduled
  ⚪  Bus BUS02  | Route-B       | Scheduled
  ⚪  Bus BUS03  | Route-A       | Scheduled

--- Student Login: S001 / pass1 ---

  ✔ Welcome, Alice!

  ┌─── STUDENT MENU ───────────────────┐
  │  1. View My Bus & Seat Details
  │  2. Update Transport Preferences
  │  3. View Live Bus Tracking
  │  4. View Seat Matrix of My Bus
  │  0. Logout
  └──────────────────────────────────┘
  Choice: 1

  ┌─────────────────────────────────────┐
  │ Name    : Alice                     │
  │ ID      : S001                      │
  │ Route   : Route-A                   │
  │ Contact : 9900001111                │
  │ Bus     : BUS01                     │
  │ Seat    : 1                         │
  └─────────────────────────────────────┘

  Choice: 4

  === Seat Matrix: Bus BUS01 ===
  [O = Available]  [X = Occupied]

  [ 1:X]  [ 2:X]  [ 3:X]  [ 4:O]  [ 5:O]
  [ 6:O]  [ 7:O]  [ 8:O]  [ 9:O]  [10:O]

--- Admin: Simulate Bus Status ---

  Bus Number: BUS01
  ✔ Bus BUS01 status updated → Arriving

  🟡  Bus BUS01  | Route-A       | Arriving
```

---

## Viva Questions You Should Know

1. **Why HashMap instead of ArrayList for students/buses?**
   → O(1) lookup by ID/number vs O(n) linear search.

2. **How is duplicate seat assignment prevented?**
   → `SeatManager` uses a `boolean[]` per bus; a seat is only allocated if `seats[i] == false`.

3. **How does the tracking simulation work?**
   → `Bus` holds a `STATUS_CYCLE` array. `advanceStatus()` increments the index cyclically.

4. **What is the role of `Admin.java`?**
   → It's the service/controller layer — owns all data structures and enforces business rules.

5. **How would you add file persistence?**
   → Serialize `students` and `buses` maps using `ObjectOutputStream` / `FileOutputStream` on exit, and deserialize on startup.
