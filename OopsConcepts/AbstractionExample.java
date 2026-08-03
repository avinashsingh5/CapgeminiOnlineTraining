package OopsConcepts;

abstract class Payment {

    public abstract void makePayment(double amount);

    public void printReceipt() {
        System.out.println("Payment receipt generated");
    }
}

class UpiPayment extends Payment {

    @Override
    public void makePayment(double amount) {
        System.out.println("Paid ₹" + amount + " using UPI");
    }
}

class CardPayment extends Payment {

    @Override
    public void makePayment(double amount) {
        System.out.println("Paid ₹" + amount + " using card");
    }
}

public class AbstractionExample {

    public static void main(String[] args) {

        Payment payment = new UpiPayment();

        payment.makePayment(2500);
        payment.printReceipt();
    }
}