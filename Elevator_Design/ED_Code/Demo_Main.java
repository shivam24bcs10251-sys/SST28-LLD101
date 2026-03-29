import controllers.ElevatorController;
import controllers.ElevatorSystem;
import enums.Direction;
import models.ElevatorCar;
import models.Floor;
import models.Request;
import strategies.FCFSStrategy;
import strategies.OddEvenStrategy;

import java.util.ArrayList;
import java.util.List;

public class Demo_Main {

    static List<ElevatorController> controllers = new ArrayList<>();
    static ElevatorSystem system;

    // Re-initialize system before each scenario
    static void setup() {
        controllers.clear();
        ElevatorSystem.reset(); // we'll add a reset method

        int numFloors = 10;
        int weightCapacity = 500;

        List<Floor> floors = new ArrayList<>();
        for (int i = 0; i <= numFloors; i++) floors.add(new Floor(i));

        for (int i = 1; i <= 3; i++) {
            ElevatorCar car = new ElevatorCar(i, numFloors, weightCapacity);
            controllers.add(new ElevatorController(car, new FCFSStrategy()));
        }

        ElevatorSystem.initialize(controllers, floors, new OddEvenStrategy());
        system = ElevatorSystem.getInstance();
    }

    static void pause(int ms) {
        try { Thread.sleep(ms); } catch (InterruptedException ignored) {}
    }

    static void printDivider(String title) {
        System.out.println("\n╔══════════════════════════════════════════════════════╗");
        System.out.printf( "║  %-52s║%n", " SCENARIO: " + title);
        System.out.println("╚══════════════════════════════════════════════════════╝");
        pause(400);
    }

    static void printStatus() {
        System.out.println("\n  ┌─────────────────────────────────────────────────┐");
        System.out.println("  │                  ELEVATOR STATUS                 │");
        System.out.println("  ├────────────┬──────────┬──────────────┬───────────┤");
        System.out.println("  │ Elevator   │  Floor   │    State     │  Weight   │");
        System.out.println("  ├────────────┼──────────┼──────────────┼───────────┤");
        for (ElevatorController c : controllers) {
            ElevatorCar car = c.getElevatorCar();
            System.out.printf("  │  Elev %-3d  │  %-6d  │  %-10s  │ %3d/%-3d kg│%n",
                car.getId(), car.getCurrentFloor(),
                car.getState(), car.getCurrentWeight(), car.getWeightCapacity());
        }
        System.out.println("  └────────────┴──────────┴──────────────┴───────────┘");
        pause(400);
    }

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════════════╗");
        System.out.println("║           ELEVATOR SYSTEM — DEMO WALKTHROUGH         ║");
        System.out.println("║    3 Elevators  |  10 Floors  |  500kg Capacity      ║");
        System.out.println("╚══════════════════════════════════════════════════════╝");
        pause(600);

        // ─────────────────────────────────────────
        // SCENARIO 1: Basic Hall Call (FCFS)
        // ─────────────────────────────────────────
        setup();
        printDivider("1 — Basic Hall Call & Ride (FCFS)");
        System.out.println("  → Person on Floor 2 presses UP button.");
        system.acceptHallCall(2, Direction.UP);
        processAll();

        System.out.println("\n  → Passenger boards Elevator 1 and selects Floor 7.");
        ElevatorController e1 = controllers.get(0);
        e1.submitRequest(new Request(e1.getElevatorCar().getCurrentFloor(), 7, Direction.UP));
        e1.processRequests();
        printStatus();
        pause(300);

        // ─────────────────────────────────────────
        // SCENARIO 2: Two Concurrent Calls (Up + Down)
        // ─────────────────────────────────────────
        setup();
        printDivider("2 — Two Concurrent Hall Calls (UP + DOWN)");
        System.out.println("  → Floor 5 presses UP. Floor 5 also presses DOWN.");
        System.out.println("  → System should dispatch two different elevators.");
        system.acceptHallCall(5, Direction.UP);
        system.acceptHallCall(5, Direction.DOWN);
        processAll();
        printStatus();
        pause(300);

        // ─────────────────────────────────────────
        // SCENARIO 3: Elevator Under Maintenance
        // ─────────────────────────────────────────
        setup();
        printDivider("3 — Maintenance Mode");
        System.out.println("  → Putting Elevator 2 under maintenance.");
        controllers.get(1).setUnderMaintenance(true);
        printStatus();

        System.out.println("\n  → Someone calls from Floor 8 DOWN.");
        System.out.println("  → System must skip Elevator 2 and route elsewhere.");
        system.acceptHallCall(8, Direction.DOWN);
        processAll();
        printStatus();
        pause(300);

        // ─────────────────────────────────────────
        // SCENARIO 4: Weight Limit Exceeded
        // ─────────────────────────────────────────
        setup();
        printDivider("4 — Weight Limit Check");
        System.out.println("  → Elevator 1 is currently at Floor 3.");
        ElevatorController e1w = controllers.get(0);
        e1w.submitRequest(new Request(0, 3, Direction.UP));
        e1w.processRequests();

        System.out.println("\n  → Adding 480kg of passengers to Elevator 1 (limit: 500kg).");
        e1w.getElevatorCar().addWeight(480);
        printStatus();

        System.out.println("\n  → Adding 50kg more... WARNING: Overweight!");
        e1w.getElevatorCar().addWeight(50);
        if (e1w.getElevatorCar().isOverweight()) {
            System.out.println("  ⚠  OVERWEIGHT ALERT! Elevator 1 is over capacity. Doors held open.");
        }
        printStatus();

        System.out.println("\n  → Removing 100kg as passenger exits...");
        e1w.getElevatorCar().removeWeight(100);
        System.out.println("  ✓  Weight normalised. Elevator can proceed.");
        printStatus();
        pause(300);

        // ─────────────────────────────────────────
        // SCENARIO 5: Emergency Alarm
        // ─────────────────────────────────────────
        setup();
        printDivider("5 — Emergency Alarm");

        System.out.println("  → Elevator 1 is called to Floor 9.");
        controllers.get(0).submitRequest(new Request(0, 9, Direction.UP));
        controllers.get(0).processRequests();

        System.out.println("\n  → Elevator 2 is called to Floor 4.");
        controllers.get(1).submitRequest(new Request(0, 4, Direction.UP));
        controllers.get(1).processRequests();

        printStatus();

        System.out.println("\n  → ALARM BUTTON PRESSED — Emergency halt!");
        system.triggerAlarm();
        printStatus();

        System.out.println("\n  → Trying to call elevator during alarm — should be denied.");
        system.acceptHallCall(3, Direction.UP);

        System.out.println("\n  → Alarm reset by technician.");
        system.resetAlarm();
        System.out.println("  → System back to normal. Hall calls accepted again.");
        system.acceptHallCall(3, Direction.UP);
        processAll();
        printStatus();

        System.out.println("\n╔══════════════════════════════════════════════════════╗");
        System.out.println("║             DEMO WALKTHROUGH COMPLETE                ║");
        System.out.println("╚══════════════════════════════════════════════════════╝");
    }

    static void processAll() {
        for (ElevatorController c : controllers) {
            c.processRequests();
        }
    }
}
