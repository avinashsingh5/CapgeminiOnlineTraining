package OopsConcepts;

class Calculator {

    public int add(int firstNumber, int secondNumber) {
        return firstNumber + secondNumber;
    }

    public int add(int firstNumber,int secondNumber,int thirdNumber) {
        return firstNumber + secondNumber + thirdNumber;
    }

    public double add(double firstNumber, double secondNumber) {
        return firstNumber + secondNumber;
    }
}

public class CompileTimePolymorphism {

    public static void main(String[] args) {

        Calculator calculator = new Calculator();

        int twoNumberSum = calculator.add(10, 20);
        int threeNumberSum = calculator.add(10, 20, 30);
        double decimalSum = calculator.add(10.5, 20.5);

        System.out.println("Two numbers: " + twoNumberSum);
        System.out.println("Three numbers: " + threeNumberSum);
        System.out.println("Decimal numbers: " + decimalSum);
    }
}