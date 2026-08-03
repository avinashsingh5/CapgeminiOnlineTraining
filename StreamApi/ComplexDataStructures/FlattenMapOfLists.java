package StreamApi.ComplexDataStructures;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FlattenMapOfLists {

    public static void main(String[] args) {

        Map<String, List<Integer>> numbersByGroup = new LinkedHashMap<>();

        numbersByGroup.put("First Group", List.of(10, 20, 30));
        numbersByGroup.put("Second Group", List.of(40, 50));
        numbersByGroup.put("Third Group", List.of(60, 70, 80));

        List<Integer> allNumbers = numbersByGroup.values()
                .stream()
                .flatMap(numberList -> numberList.stream())
                .toList();

        System.out.println("Original map: " + numbersByGroup);
        System.out.println("All numbers: " + allNumbers);
    }
}