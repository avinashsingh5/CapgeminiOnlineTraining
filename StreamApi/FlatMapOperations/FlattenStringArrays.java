package StreamApi.FlatMapOperations;

import java.util.Arrays;
import java.util.List;

public class FlattenStringArrays {

    public static void main(String[] args) {

        List<String[]> technologyGroups = List.of(
                new String[]{"Java", "Spring Boot"},
                new String[]{"HTML", "CSS", "React"},
                new String[]{"MySQL", "MongoDB"}
        );

        List<String> allTechnologies = technologyGroups.stream()
                .flatMap(technologyArray -> Arrays.stream(technologyArray))
                .toList();

        System.out.println("All technologies: " + allTechnologies);
    }
}