import java.util.Map;

public class TrackingSimulator {

    private Map<String, Bus> busMap;

    public TrackingSimulator(Map<String, Bus> busMap) {
        this.busMap = busMap;
    }

    public void showAllStatus() {
        System.out.println("\n  ╔═══════════════════════════════════════════╗");
        System.out.println("  ║          LIVE BUS TRACKING STATUS          ║");
        System.out.println("  ╚═══════════════════════════════════════════╝");
        if (busMap.isEmpty()) {
            System.out.println("  No buses registered.");
            return;
        }
        for (Bus bus : busMap.values()) {
            System.out.printf("  %s  Bus %-6s | %-12s | %s%n",
                statusIcon(bus.getStatus()), bus.getBusNumber(),
                bus.getRoute(), bus.getStatus());
        }
        System.out.println();
    }

    public void showStatusForBus(String busNumber) {
        Bus bus = busMap.get(busNumber);
        if (bus == null) { System.out.println("  Bus not found."); return; }
        System.out.printf("  %s  Bus %s [%s] -> %s%n",
            statusIcon(bus.getStatus()), bus.getBusNumber(), bus.getRoute(), bus.getStatus());
    }

    public void advanceBusStatus(String busNumber) {
        Bus bus = busMap.get(busNumber);
        if (bus == null) { System.out.println("  Bus not found."); return; }
        bus.advanceStatus();
        System.out.println("  ✔ Bus " + busNumber + " status updated → " + bus.getStatus());
    }

    private String statusIcon(String status) {
        switch (status) {
            case "Arriving":  return "🟡";
            case "Departed":  return "🔵";
            case "Reached":   return "🟢";
            default:          return "⚪";
        }
    }
}
