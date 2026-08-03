package StreamApi.FlatMapOperations;


import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FlattenMapValues {

    public static void main(String[] args) {

        Map<String, List<String>> skillsByCategory = new LinkedHashMap<>();

        skillsByCategory.put(
                "Frontend",
                List.of("HTML", "CSS", "React")
        );

        skillsByCategory.put(
                "Backend",
                List.of("Java", "Spring Boot")
        );

        skillsByCategory.put(
                "Database",
                List.of("MySQL", "MongoDB")
        );

        List<String> allSkills = skillsByCategory.values()
                .stream()
                .flatMap(skillList -> skillList.stream())
                .toList();

        System.out.println("Skills map: " + skillsByCategory);
        System.out.println("All skills: " + allSkills);
    }
}
