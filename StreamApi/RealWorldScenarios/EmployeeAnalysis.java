package StreamApi.RealWorldScenarios;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class Employee {

    private int employeeId;
    private String employeeName;
    private String department;
    private double salary;
    private LocalDate joiningDate;

    public Employee(
            int employeeId,
            String employeeName,
            String department,
            double salary,
            LocalDate joiningDate
    ) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.department = department;
        this.salary = salary;
        this.joiningDate = joiningDate;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public String getDepartment() {
        return department;
    }

    public double getSalary() {
        return salary;
    }

    public LocalDate getJoiningDate() {
        return joiningDate;
    }
}

public class EmployeeAnalysis {

    public static void main(String[] args) {

        List<Employee> employees = List.of(
                new Employee(
                        101,
                        "Avinash",
                        "Development",
                        60000,
                        LocalDate.of(2024, 5, 10)
                ),

                new Employee(
                        102,
                        "Rahul",
                        "Development",
                        80000,
                        LocalDate.of(2022, 8, 15)
                ),

                new Employee(
                        103,
                        "Neha",
                        "Testing",
                        50000,
                        LocalDate.of(2023, 2, 20)
                ),

                new Employee(
                        104,
                        "Amit",
                        "Testing",
                        70000,
                        LocalDate.of(2018, 7, 12)
                ),

                new Employee(
                        105,
                        "Priya",
                        "HR",
                        45000,
                        LocalDate.of(2025, 1, 5)
                )
        );

        LocalDate fiveYearsAgo = LocalDate.now().minusYears(5);

        Map<String, Double> averageSalaryByDepartment = employees.stream()
                .filter(employee ->
                        !employee.getJoiningDate().isBefore(fiveYearsAgo)
                )
                .collect(Collectors.groupingBy(
                        employee -> employee.getDepartment(),
                        Collectors.averagingDouble(employee -> employee.getSalary())
                ));

        System.out.println("Employees hired after: " + fiveYearsAgo);

        averageSalaryByDepartment.forEach((department, averageSalary) ->
                System.out.println(
                        department + " average salary: ₹" + averageSalary
                )
        );
    }
}