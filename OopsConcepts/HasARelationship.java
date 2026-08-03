package OopsConcepts;

class Engine {

    public void startEngine() {
        System.out.println("Engine started");
    }
}

class CarWithEngine {

    private Engine engine;

    public CarWithEngine() {
        engine = new Engine();
    }

    public void startCar() {
        engine.startEngine();
        System.out.println("Car started");
    }
}

public class HasARelationship {

    public static void main(String[] args) {

        CarWithEngine car = new CarWithEngine();

        car.startCar();
    }
}