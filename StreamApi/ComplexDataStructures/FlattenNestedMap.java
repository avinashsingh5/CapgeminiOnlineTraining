package StreamApi.ComplexDataStructures;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

class FlatMapEntry {

    private String department;
    private String employeeName;
    private int salary;

    public FlatMapEntry(
            String department,
            String employeeName,
            int salary
    ) {
        this.department = department;
        this.employeeName = employeeName;
        this.salary = salary;
    }

    @Override
    public String toString() {
        return department
                + " - "
                + employeeName
                + " - "
                + salary;
    }
}

public class FlattenNestedMap {

    public static void main(String[] args) {

        Map<String, Map<String, Integer>> companyData =
                new LinkedHashMap<>();

        Map<String, Integer> developmentEmployees =
                new LinkedHashMap<>();

        developmentEmployees.put("Avinash", 70000);
        developmentEmployees.put("Rahul", 80000);

        Map<String, Integer> testingEmployees =
                new LinkedHashMap<>();

        testingEmployees.put("Neha", 55000);
        testingEmployees.put("Priya", 65000);

        companyData.put("Development", developmentEmployees);
        companyData.put("Testing", testingEmployees);

        List<FlatMapEntry> flatEntries = companyData.entrySet()
                .stream()
                .flatMap(departmentEntry -> {

                    String departmentName =
                            departmentEntry.getKey();

                    Map<String, Integer> employees =
                            departmentEntry.getValue();

                    return employees.entrySet()
                            .stream()
                            .map(employeeEntry ->
                                    new FlatMapEntry(
                                            departmentName,
                                            employeeEntry.getKey(),
                                            employeeEntry.getValue()
                                    )
                            );
                })
                .toList();

        flatEntries.forEach(entry ->
                System.out.println(entry)
        );
    }
}