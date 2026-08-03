package StreamApi.ComplexDataStructures;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class CompanyEmployee {

    private int employeeId;
    private String employeeName;
    private String department;
    private double salary;

    public CompanyEmployee(
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
        return employeeName + " - ₹" + salary;
    }
}

public class GroupEmployeesBySalaryRange {

    public static void main(String[] args) {

        List<CompanyEmployee> employees = List.of(
                new CompanyEmployee(
                        101,
                        "Avinash",
                        "Development",
                        45000
                ),
                new CompanyEmployee(
                        102,
                        "Rahul",
                        "Development",
                        75000
                ),
                new CompanyEmployee(
                        103,
                        "Amit",
                        "Development",
                        120000
                ),
                new CompanyEmployee(
                        104,
                        "Neha",
                        "Testing",
                        48000
                ),
                new CompanyEmployee(
                        105,
                        "Priya",
                        "Testing",
                        65000
                ),
                new CompanyEmployee(
                        106,
                        "Rohit",
                        "Testing",
                        110000
                )
        );

        Map<String, Map<String, List<CompanyEmployee>>> groupedEmployees =
                employees.stream()
                        .collect(Collectors.groupingBy(
                                employee ->
                                        employee.getDepartment(),

                                Collectors.groupingBy(
                                        employee ->
                                                getSalaryRange(
                                                        employee.getSalary()
                                                )
                                )
                        ));

        groupedEmployees.forEach(
                (department, salaryGroups) -> {

                    System.out.println(
                            "\nDepartment: " + department
                    );

                    salaryGroups.forEach(
                            (salaryRange, employeeList) -> {

                                System.out.println(
                                        salaryRange
                                                + ": "
                                                + employeeList
                                );
                            }
                    );
                }
        );
    }

    private static String getSalaryRange(double salary) {

        if (salary < 50000) {
            return "Below 50000";
        }

        if (salary <= 100000) {
            return "50000 - 100000";
        }

        return "Above 100000";
    }
}