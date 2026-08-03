package StreamApi.FlatMapOperations;

import java.util.List;

public class FlattenIntegerLists {

    public static void main(String[] args) {

        List<List<Integer>> numberGroups = List.of(
                List.of(10, 20, 30),
                List.of(40, 50),
                List.of(60, 70, 80)
        );

        List<Integer> allNumbers = numberGroups.stream()
                .flatMap(numberList -> numberList.stream())
                .toList();

        System.out.println("Nested list: " + numberGroups);
        System.out.println("Single list: " + allNumbers);
    }
}