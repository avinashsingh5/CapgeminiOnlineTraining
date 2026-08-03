package StreamApi.FlatMapOperations;

import java.util.List;

class Item {

    private String itemName;

    public Item(String itemName) {
        this.itemName = itemName;
    }

    public String getItemName() {
        return itemName;
    }
}

class Order {

    private int orderId;
    private List<Item> items;

    public Order(int orderId, List<Item> items) {
        this.orderId = orderId;
        this.items = items;
    }

    public int getOrderId() {
        return orderId;
    }

    public List<Item> getItems() {
        return items;
    }
}

public class OrderItemsExample {

    public static void main(String[] args) {

        List<Order> customerOrders = List.of(
                new Order(
                        101,
                        List.of(
                                new Item("Laptop"),
                                new Item("Mouse")
                        )
                ),
                new Order(
                        102,
                        List.of(
                                new Item("Keyboard"),
                                new Item("Monitor")
                        )
                )
        );

        List<String> allItemNames = customerOrders.stream()
                .flatMap(order -> order.getItems().stream())
                .map(item -> item.getItemName())
                .toList();

        System.out.println("All ordered items: " + allItemNames);
    }
}