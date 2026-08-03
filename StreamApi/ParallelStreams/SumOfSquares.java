package StreamApi.ParallelStreams;

import java.util.stream.LongStream;

public class SumOfSquares {

    public static void main(String[] args) {

        long sumOfSquares = LongStream.rangeClosed(1, 1_000_000)
                .parallel()
                .map(number -> number * number)
                .sum();

        System.out.println("Sum of squares: " + sumOfSquares);
    }
}