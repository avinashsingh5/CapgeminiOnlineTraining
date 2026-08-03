package StreamApi.ComplexDataStructures;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class CsvEmployee {

    private int employeeId;
    private String employeeName;
    private String department;
    private double salary;

    public CsvEmployee(
            int employeeId,
            String employeeName,
            String department,
            double salary
    ) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.department = department;
        this.salary = salary;
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

    @Override
    public String toString() {
        return employeeId
                + ", "
                + employeeName
                + ", "
                + department
                + ", "
                + salary;
    }
}

public class CsvEmployeeAnalysis {

    public static void main(String[] args) {

        List<String[]> csvRows = List.of(
                new String[]{
                        "101",
                        "Avinash",
                        "Development",
                        "70000"
                },
                new String[]{
                        "102",
                        "Rahul",
                        "Development",
                        "90000"
                },
                new String[]{
                        "103",
                        "Neha",
                        "Testing",
                        "55000"
                },
                new String[]{
                        "104",
                        "Priya",
                        "Testing",
                        "65000"
                },
                new String[]{
                        "105",
                        "Amit",
                        "HR",
                        "50000"
                }
        );

        List<CsvEmployee> employees = csvRows.stream()
                .map(row -> {

                    int employeeId =
                            Integer.parseInt(row[0]);

                    String employeeName = row[1];
                    String department = row[2];

                    double salary =
                            Double.parseDouble(row[3]);

                    return new CsvEmployee(
                            employeeId,
                            employeeName,
                            department,
                            salary
                    );
                })
                .toList();

        System.out.println("Converted employee objects:");

        employees.forEach(employee ->
                System.out.println(employee)
        );

        Map<String, Double> averageSalaryByDepartment =
                employees.stream()
                        .collect(Collectors.groupingBy(
                                employee ->
                                        employee.getDepartment(),

                                Collectors.averagingDouble(
                                        employee ->
                                                employee.getSalary()
                                )
                        ));

        System.out.println(
                "\nAverage salary by department:"
        );

        averageSalaryByDepartment.forEach(
                (department, averageSalary) ->
                        System.out.println(
                                department
                                        + ": ₹"
                                        + averageSalary
                        )
        );

        CsvEmployee highestPaidEmployee =
                employees.stream()
                        .max((firstEmployee, secondEmployee) ->
                                Double.compare(
                                        firstEmployee.getSalary(),
                                        secondEmployee.getSalary()
                                )
                        )
                        .orElse(null);

        if (highestPaidEmployee != null) {

            System.out.println(
                    "\nHighest-paid employee: "
                            + highestPaidEmployee.getEmployeeName()
                            + " - ₹"
                            + highestPaidEmployee.getSalary()
            );
        }
    }
}