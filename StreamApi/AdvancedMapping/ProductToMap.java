package StreamApi.AdvancedMapping;
import java.util.*;
import java.util.stream.Collectors;

public class ProductToMap {
    public static void main(String[] args) {

        List<Product> products = Arrays.asList(
                new Product("Laptop", 65000),
                new Product("Mouse", 800),
                new Product("Keyboard", 1500)
        );

        Map<String, Double> map = products.stream()
                .collect(Collectors.toMap(
                        p -> p.getName(),
                        p -> p.getPrice()
                ));

        System.out.println(map);
    }
}