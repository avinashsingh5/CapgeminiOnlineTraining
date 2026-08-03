package StreamApi.ParallelStreams;

import java.util.List;
import java.util.stream.IntStream;

public class FilterAndSortLargeData {

    public static void main(String[] args) {

        List<Integer> numbers = IntStream.rangeClosed(1, 1_000_000)
                .boxed()
                .toList();

        List<Integer> filteredNumbers = numbers.parallelStream()
                .filter(number -> number > 900_000)
                .filter(number -> number % 2 == 0)
                .sorted((firstNumber, secondNumber) ->
                        secondNumber.compareTo(firstNumber)
                )
                .limit(20)
                .toList();

        System.out.println("First 20 results:");
        System.out.println(filteredNumbers);
    }
}