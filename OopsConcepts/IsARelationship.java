package OopsConcepts;

class Vehicle {

    public void start() {
        System.out.println("Vehicle started");
    }
}

class Car extends Vehicle {

    public void drive() {
        System.out.println("Car is moving");
    }
}

public class IsARelationship {

    public static void main(String[] args) {

        Car car = new Car();

        car.start();
        car.drive();
    }
}