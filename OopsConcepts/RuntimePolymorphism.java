package OopsConcepts;

class Animal {

    public void makeSound() {
        System.out.println("Animal makes a sound");
    }
}

class Dog extends Animal {

    @Override
    public void makeSound() {
        System.out.println("Dog barks");
    }
}

class Cat extends Animal {

    @Override
    public void makeSound() {
        System.out.println("Cat meows");
    }
}

public class RuntimePolymorphism {

    public static void main(String[] args) {

        Animal firstAnimal = new Dog();
        Animal secondAnimal = new Cat();

        firstAnimal.makeSound();
        secondAnimal.makeSound();
    }
}