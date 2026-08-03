package OopsConcepts;

class Customer {

    private String customerName;
    private String city;

    public Customer(String customerName) {

        this.customerName = customerName;
        this.city = "Not provided";
    }

    public Customer(String customerName,String city) {
        this.customerName = customerName;
        this.city = city;
    }

    public void displayCustomer() {

        System.out.println(
                customerName + " - " + city
        );
    }
}

public class ConstructorOverloading {

    public static void main(String[] args) {

        Customer firstCustomer = new Customer("Avinash");

        Customer secondCustomer =
                new Customer("Rahul", "Delhi");

        firstCustomer.displayCustomer();
        secondCustomer.displayCustomer();
    }
}