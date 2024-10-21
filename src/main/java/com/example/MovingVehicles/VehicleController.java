package com.example.MovingVehicles;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Controller
@RequestMapping("/vehicle")
public class VehicleController {
    private List<Vehicle> vehicles = new ArrayList<>(); // for storing all created vehicle instances
    private ExecutorService executorService = Executors.newCachedThreadPool();  // to manage threads for running vehicles concurrently

    /**
     * Adds a new vehicle to the simulation.
     * A new vehicle is instantiated with a default position and added to the list.
     * The vehicle's thread is submitted to the executor service, so it starts running in the background.
     *
     * @return a message confirming the addition of the vehicle
     */
    @PostMapping("/add")
    @ResponseBody
    public String addVehicle() {
        String vehicleId = "Vehicle" + (vehicles.size() + 1); // generate an ID for a new vehicle
        Vehicle vehicle = new Vehicle(vehicleId, 400, 300);  // creates a new vehicle in the middle of the canvas
        vehicles.add(vehicle);
        executorService.submit(vehicle); // submit the vehicle to run in a separate thread
        return "Vehicle added: " + vehicleId;
    }

    /**
     * Retrieves the positions of all vehicles.
     *
     * @return a list of Vehicle objects with their current positions
     */
    @GetMapping("/positions")
    @ResponseBody
    public List<Vehicle> getVehiclePositions() {
        return vehicles;
    }

    /**
     * Sets the direction of a specific vehicle.
     * It searches for the vehicle by the ID, then updates its direction if the vehicle is found.
     *
     * @param vehicleId the ID of the vehicle whose direction needs to be changed
     * @param direction the new direction to set for the vehicle
     * @return a message confirming the direction change or an error message if the vehicle is not found
     */
    @PostMapping("/direction")
    @ResponseBody
    public String setDirection(@RequestParam String vehicleId, @RequestParam String direction) {
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getVehicleId().equals(vehicleId)) {
                vehicle.setDirection(direction);
                return "Direction set for " + vehicleId + " to " + direction;
            }
        }
        return "Vehicle not found.";
    }
}
