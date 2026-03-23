# 🚗 Parking Lot System (LLD)

## 📌 Problem Statement
Design and implement a **Parking Lot Management System** that supports:
- Parking and unparking of vehicles  
- Parking ticket generation  
- Fee calculation  
- Management of multiple floors and spot types  

---

## 📋 Requirements

### 🏢 Multiple Floors
- The parking lot can have multiple floors.

### 🅿️ Parking Spots
- Each floor has multiple parking spots of different types:
  - Car  
  - Bike  
  - Truck  

### 🚘 Vehicle Types
- Support for different vehicle types (`vehicletype/`).

### 🎫 Ticketing
- Generate a **parking ticket** when a vehicle is parked.

### 🚪 Unparking
- Allow vehicles to unpark and calculate the parking fee.

### 💰 Fee Calculation
- Support multiple fee strategies (`fee/`).

### 🎯 Spot Allocation
- Allocate the **nearest available spot** of the correct type.

### 🔧 Extensibility
- Easily add:
  - New vehicle types  
  - New spot types  
  - New fee strategies  

---

## 🧱 Core Entities

### 1. ParkingLot
- Main class managing the entire parking system.

### 2. ParkingFloor
- Represents a single floor in the parking lot.

### 3. ParkingSpot
- Represents an individual parking spot.

### 4. Ticket
- Represents a parking ticket issued during parking.

### 5. VehicleType (`vehicletype/`)
- Enum or classes for different vehicle types.

### 6. Fee Calculation (`fee/`)
- Classes responsible for calculating parking fees.

---

## 🧩 Class Design

### 1️⃣ ParkingLot

**Methods:**
- `parkVehicle(Vehicle vehicle)`
- `unparkVehicle(String ticketId)`
- `addFloor(ParkingFloor floor)`
- `getAvailableSpots()`

**Fields:**
- List of floors  
- Mapping of tickets  

---

### 2️⃣ ParkingFloor

**Methods:**
- `getAvailableSpot(VehicleType type)`
- `parkVehicle(Vehicle vehicle)`
- `unparkVehicle(String spotId)`

**Fields:**
- List of parking spots  
- Floor number  

---

### 3️⃣ ParkingSpot

**Methods:**
- `isAvailable()`
- `assignVehicle(Vehicle vehicle)`
- `removeVehicle()`

**Fields:**
- Spot ID  
- Spot type  
- Current vehicle  

---

### 4️⃣ Ticket

**Fields:**
- Ticket ID  
- Vehicle info  
- Entry time  
- Spot info  

---

### 5️⃣ VehicleType (`vehicletype/`)

- Enum or class:
  - `CAR`
  - `BIKE`
  - `TRUCK`

---

### 6️⃣ Fee Calculation (`fee/`)

**Methods:**
- `calculateFee(Ticket parkingTicket, Date exitTime)`

**Features:**
- Strategy-based implementation  
- Easy to extend with new pricing models  

---

## 🧠 Design Patterns Used

### 🎯 Strategy Pattern
- Used for different fee calculation strategies.

### 🏭 Factory Pattern
- (Optional) Used for creating vehicles or parking spots.

### 🔒 Singleton Pattern
- (Optional) Ensures only one instance of `ParkingLot`.

---

## 💻 Example Usage

```java
ParkingLot lot = new ParkingLot();
lot.addFloor(new ParkingFloor(...));

Ticket parkingTicket = lot.parkVehicle(new Car("KA-01-1234"));

lot.unparkVehicle(parkingTicket.getId());