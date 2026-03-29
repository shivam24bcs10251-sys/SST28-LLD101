# Elevator System Low Level Design (LLD)

This is the Elevator LLD assignment implementation representing the design and working code of an Elevator system.

## Requirements Covered
1. **Different Strategies**: Supporting `First Come First Serve (FCFS)` and `SeekFirst` (LOOK/SCAN algorithm via an Interface).
2. **Multiple Elevators per Floor**: An `ElevatorSystem` controls multiple `ElevatorController` instances to handle `ElevatorCar`s.
3. **Common Hall Buttons**: A single common button (Up/Down) outside for all elevators is supported. Floors contain `HallButton`.
4. **Optimal Dispatching**: The singleton `ElevatorSystem` delegates the call to only one optimal elevator using an `ElevatorSelectionStrategy` (like Odd/Even strategy).
5. **Emergency Alarm**: An `alarmTriggered()` method halts all elevators and sounds the bell. It stops them from processing normal requests.
6. **Concurrent Requests**: If two different requests are received (e.g., UP and DOWN), the `ElevatorSystem` can dispatch two different elevators to handle them individually.
7. **Weight Constraints**: The `ElevatorCar` has a `weightCapacity` and `currentWeight`, preventing it from taking passengers if `isOverweight()` returns `true`.
8. **Destination Selection**: Passengers use `InternalButton` inside the `ElevatorCar` to choose their destinations.
9. **Maintenance State**: Elevators have an `UNDER_MAINTENANCE` state where they reject all incoming assignments and stop operating.
10. **States**: `MOVING_UP`, `MOVING_DOWN`, `IDLE`, and `UNDER_MAINTENANCE` are modelled via an `ElevatorState` Enumeration.

## Code Structure

The source code is organized inside the `ED_Code` folder.
It follows the Model-View-Controller (MVC) and Strategy patterns for flexibility.
- `enums/`: Contains representations for `ElevatorState` and `Direction`.
- `models/`: Entities for `ElevatorCar`, `Floor`, and `Request`.
- `buttons/`: Abstractions mapping `InternalButton` and `HallButton` to physical pressing behaviours.
- `strategies/`: Modular algorithms separating `RequestProcessingStrategy` from `ElevatorSelectionStrategy`.
- `controllers/`: System managers (`ElevatorController` for a single car, `ElevatorSystem` for global dispatching).

## Execution
Navigate to the `ED_Code` directory to compile and view the working implementation outputs.

```bash
cd ED_Code
javac -d bin Main.java buttons/*.java controllers/*.java enums/*.java models/*.java strategies/*.java
java -cp bin Main
```

## UML Diagram
The file `ED_Code/elevator_uml.xml` contains the Draw.io compatible XML for the Class Diagram modeling the relationships between these features. You can import this explicitly into draw.io to view and edit the visual representation.
