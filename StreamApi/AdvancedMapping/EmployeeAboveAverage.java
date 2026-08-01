package StreamApi.AdvancedMapping;
import java.util.*;
import java.util.stream.Collectors;

public class EmployeeAboveAverage {
    public static void main(String[] args) {

        List<Employee> employees = Arrays.asList(
                new Employee("Aman", 50000, "HR"),
                new Employee("Rohan", 70000, "IT"),
                new Employee("Avinash", 60000, "Finance"),
                new Employee("Akash", 80000, "IT")
        );

        double avg = employees.stream()
                .mapToDouble(e -> e.getSalary())
                .average()
                .orElse(0);

        List<String> result = employees.stream()
                .filter(e -> e.getSalary() > avg)
                .map(e -> e.getName())
                .collect(Collectors.toList());

        System.out.println(result);
    }
}