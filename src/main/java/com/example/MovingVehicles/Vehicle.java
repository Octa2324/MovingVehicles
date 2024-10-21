package com.example.MovingVehicles;

public class Vehicle implements Runnable {
    private String vehicleId;
    private int x; // current x-coordinate of the vehicle
    private int y; // current y-coordinate of the vehicle
    private String direction;
    private boolean running;  // indicates if the vehicle is running or not

    public Vehicle(String vehicleId, int startX, int startY) {
        this.vehicleId = vehicleId;
        this.x = startX;
        this.y = startY;
        this.direction = "UP"; // for when the vehicle spawn the default direction is up
        this.running = true;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public void run() {
        while (running) {
            move();
            try {
                Thread.sleep(1000);  // sleep for 1 second between moves
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // synchronized to ensure thread safety when the direction is modified from different threads
    private synchronized void move() {
        switch (direction) {
            case "UP": y -= 10; break;
            case "DOWN": y += 10; break;
            case "LEFT": x -= 10; break;
            case "RIGHT": x += 10; break;
        }
        // log the vehicle`s new position and direction
        System.out.println(vehicleId + " moved to position: (" + x + ", " + y + ") in direction " + direction);
    }
}
