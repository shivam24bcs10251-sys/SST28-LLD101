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
import java.util.Scanner;

public class Main {

    static List<ElevatorController> controllers = new ArrayList<>();
    static ElevatorSystem system;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║       ELEVATOR SYSTEM SIMULATOR          ║");
        System.out.println("╚══════════════════════════════════════════╝");

        System.out.print("\nEnter total number of floors: ");
        int numFloors = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Enter number of elevators: ");
        int numElevators = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Enter weight capacity per elevator (kg): ");
        int weightCapacity = Integer.parseInt(scanner.nextLine().trim());

        // Initialize Floors
        List<Floor> floors = new ArrayList<>();
        for (int i = 0; i <= numFloors; i++) {
            floors.add(new Floor(i));
        }

        // Initialize Elevators with FCFS Strategy
        for (int i = 1; i <= numElevators; i++) {
            ElevatorCar car = new ElevatorCar(i, numFloors, weightCapacity);
            ElevatorController controller = new ElevatorController(car, new FCFSStrategy());
            controllers.add(controller);
        }

        // Initialize global system with OddEven proximity selection
        ElevatorSystem.initialize(controllers, floors, new OddEvenStrategy());
        system = ElevatorSystem.getInstance();

        System.out.println("\nSystem initialized with " + numElevators + " elevator(s) and " + (numFloors + 1) + " floors (0 to " + numFloors + ").");
        printHelp();

        while (true) {
            System.out.print("\n> Enter command: ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit") || input.equalsIgnoreCase("quit")) {
                System.out.println("Shutting down Elevator System. Goodbye!");
                break;
            }

            handleCommand(input, scanner);
        }

        scanner.close();
    }

    private static void handleCommand(String input, Scanner scanner) {
        String[] parts = input.split("\\s+");
        if (parts.length == 0) return;

        switch (parts[0].toLowerCase()) {

            case "call":
                // call <floor> <UP|DOWN>
                if (parts.length < 3) { System.out.println("Usage: call <floor> <UP|DOWN>"); return; }
                int callFloor = Integer.parseInt(parts[1]);
                Direction callDir = parts[2].equalsIgnoreCase("UP") ? Direction.UP : Direction.DOWN;
                system.acceptHallCall(callFloor, callDir);
                // process all controllers after a hall call
                processAll();
                break;

            case "select":
                // select <elevatorId> <destinationFloor>  — user inside elevator pressing floor button
                if (parts.length < 3) { System.out.println("Usage: select <elevatorId> <destinationFloor>"); return; }
                int elevId = Integer.parseInt(parts[1]);
                int destFloor = Integer.parseInt(parts[2]);
                ElevatorController ctrl = findController(elevId);
                if (ctrl == null) { System.out.println("Elevator " + elevId + " not found."); return; }
                ctrl.submitRequest(new Request(ctrl.getElevatorCar().getCurrentFloor(), destFloor, Direction.NONE));
                ctrl.processRequests();
                break;

            case "weight":
                // weight <elevatorId> <add|remove> <kg>
                if (parts.length < 4) { System.out.println("Usage: weight <elevatorId> <add|remove> <kg>"); return; }
                int wElevId = Integer.parseInt(parts[1]);
                int kg = Integer.parseInt(parts[3]);
                ElevatorController wCtrl = findController(wElevId);
                if (wCtrl == null) { System.out.println("Elevator " + wElevId + " not found."); return; }
                if (parts[2].equalsIgnoreCase("add")) {
                    wCtrl.getElevatorCar().addWeight(kg);
                    System.out.println("Added " + kg + "kg to Elevator " + wElevId + ". Current: " + wCtrl.getElevatorCar().getCurrentWeight() + "/" + wCtrl.getElevatorCar().getWeightCapacity() + " kg");
                    if (wCtrl.getElevatorCar().isOverweight()) System.out.println("⚠  OVERWEIGHT! Elevator " + wElevId + " cannot move.");
                } else {
                    wCtrl.getElevatorCar().removeWeight(kg);
                    System.out.println("Removed " + kg + "kg from Elevator " + wElevId + ". Current: " + wCtrl.getElevatorCar().getCurrentWeight() + "/" + wCtrl.getElevatorCar().getWeightCapacity() + " kg");
                }
                break;

            case "maintenance":
                // maintenance <elevatorId> <on|off>
                if (parts.length < 3) { System.out.println("Usage: maintenance <elevatorId> <on|off>"); return; }
                int mElevId = Integer.parseInt(parts[1]);
                ElevatorController mCtrl = findController(mElevId);
                if (mCtrl == null) { System.out.println("Elevator " + mElevId + " not found."); return; }
                mCtrl.setUnderMaintenance(parts[2].equalsIgnoreCase("on"));
                break;

            case "alarm":
                // alarm <trigger|reset>
                if (parts.length < 2) { System.out.println("Usage: alarm <trigger|reset>"); return; }
                if (parts[1].equalsIgnoreCase("trigger")) {
                    system.triggerAlarm();
                } else if (parts[1].equalsIgnoreCase("reset")) {
                    system.resetAlarm();
                }
                break;

            case "status":
                printStatus();
                break;

            case "help":
                printHelp();
                break;

            default:
                System.out.println("Unknown command. Type 'help' for usage.");
        }
    }

    private static void processAll() {
        for (ElevatorController c : controllers) {
            c.processRequests();
        }
    }

    private static ElevatorController findController(int id) {
        for (ElevatorController c : controllers) {
            if (c.getElevatorCar().getId() == id) return c;
        }
        return null;
    }

    private static void printStatus() {
        System.out.println("\n╔══════════════════════════════════════════╗");
        System.out.println("║            SYSTEM STATUS                 ║");
        System.out.println("╠══════════════════════════════════════════╣");
        for (ElevatorController c : controllers) {
            ElevatorCar car = c.getElevatorCar();
            System.out.printf("║  Elevator %-2d | Floor: %-3d | State: %-18s | Weight: %d/%d kg%n",
                car.getId(), car.getCurrentFloor(), car.getState(),
                car.getCurrentWeight(), car.getWeightCapacity());
        }
        System.out.println("╚══════════════════════════════════════════╝");
    }

    private static void printHelp() {
        System.out.println("\n──────────────────────────────────────────");
        System.out.println("  AVAILABLE COMMANDS");
        System.out.println("──────────────────────────────────────────");
        System.out.println("  call <floor> <UP|DOWN>          Hall button press on a floor");
        System.out.println("  select <elevatorId> <destFloor> Press floor button inside elevator");
        System.out.println("  weight <elevId> <add|remove> <kg> Simulate passenger weight");
        System.out.println("  maintenance <elevId> <on|off>   Toggle maintenance mode");
        System.out.println("  alarm <trigger|reset>           Emergency alarm control");
        System.out.println("  status                          Show all elevator states");
        System.out.println("  help                            Show this menu");
        System.out.println("  exit                            Quit the simulator");
        System.out.println("──────────────────────────────────────────");
    }
}
