package StreamApi.AdvancedMapping;
import java.util.*;
import java.util.stream.Collectors;

public class UniqueDepartments {
    public static void main(String[] args) {

        List<Employee> employees = Arrays.asList(
                new Employee("Amit", 50000, "HR"),
                new Employee("Bheem", 70000, "IT"),
                new Employee("RajKumar", 60000, "Finance"),
                new Employee("Avinash", 80000, "IT")
        );

        List<String> departments = employees.stream()
                .map(e -> e.getDepartment())
                .distinct()
                .collect(Collectors.toList());

        System.out.println(departments);
    }
}