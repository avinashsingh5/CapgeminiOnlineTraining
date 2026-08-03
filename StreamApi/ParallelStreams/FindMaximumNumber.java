package StreamApi.ParallelStreams;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class FindMaximumNumber {

    public static void main(String[] args) {

        Random random = new Random();

        List<Integer> numbers = IntStream.range(0, 1_000_000)
                .map(index -> random.nextInt(10_000_000))
                .boxed()
                .toList();

        int maximumNumber = numbers.parallelStream()
                .max((firstNumber, secondNumber) ->
                        Integer.compare(firstNumber, secondNumber)
                )
                .orElse(0);

        System.out.println("Maximum number: " + maximumNumber);
    }
}