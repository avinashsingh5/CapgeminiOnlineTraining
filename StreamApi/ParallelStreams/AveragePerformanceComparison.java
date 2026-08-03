package StreamApi.ParallelStreams;

import java.util.stream.LongStream;

public class AveragePerformanceComparison {

    public static void main(String[] args) {

        long dataSize = 10_000_000;

        long sequentialStartTime = System.nanoTime();

        double sequentialAverage = LongStream.rangeClosed(1, dataSize)
                .average()
                .orElse(0);

        long sequentialEndTime = System.nanoTime();

        long parallelStartTime = System.nanoTime();

        double parallelAverage = LongStream.rangeClosed(1, dataSize)
                .parallel()
                .average()
                .orElse(0);

        long parallelEndTime = System.nanoTime();

        long sequentialTime =
                (sequentialEndTime - sequentialStartTime) / 1_000_000;

        long parallelTime =
                (parallelEndTime - parallelStartTime) / 1_000_000;

        System.out.println("Sequential average: " + sequentialAverage);
        System.out.println("Sequential time: " + sequentialTime + " ms");

        System.out.println();

        System.out.println("Parallel average: " + parallelAverage);
        System.out.println("Parallel time: " + parallelTime + " ms");
    }
}