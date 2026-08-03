package StreamApi.RealWorldScenarios;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class CartItem {

    private String itemName;
    private double price;

    public CartItem(String itemName, double price) {
        this.itemName = itemName;
        this.price = price;
    }

    public String getItemName() {
        return itemName;
    }

    public double getPrice() {
        return price;
    }
}

class CustomerOrder {

    private String customerName;
    private List<CartItem> items;

    public CustomerOrder(String customerName, List<CartItem> items) {
        this.customerName = customerName;
        this.items = items;
    }

    public String getCustomerName() {
        return customerName;
    }

    public List<CartItem> getItems() {
        return items;
    }
}

public class ShoppingCartAnalysis {

    public static void main(String[] args) {

        List<CustomerOrder> orders = List.of(

                new CustomerOrder(
                        "Avinash",
                        List.of(
                                new CartItem("Laptop", 60000),
                                new CartItem("Mouse", 1000)
                        )
                ),

                new CustomerOrder(
                        "Rahul",
                        List.of(
                                new CartItem("Phone", 25000),
                                new CartItem("Charger", 1500)
                        )
                ),

                new CustomerOrder(
                        "Avinash",
                        List.of(
                                new CartItem("Keyboard", 2000),
                                new CartItem("Headphone", 3000)
                        )
                )
        );

        Map<String, Double> amountSpentByCustomer = orders.stream()
                .collect(Collectors.groupingBy(
                        order -> order.getCustomerName(),
                        Collectors.summingDouble(order ->
                                order.getItems()
                                        .stream()
                                        .mapToDouble(item -> item.getPrice())
                                        .sum()
                        )
                ));

        amountSpentByCustomer.forEach((customerName, totalAmount) ->
                System.out.println(customerName + " spent: ₹" + totalAmount)
        );
    }
}