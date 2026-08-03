package StreamApi.FlatMapOperations;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UniqueWordsFromSentences {

    public static void main(String[] args) {

        List<String> sentences = List.of(
                "Java is easy",
                "Java is powerful",
                "Streams are easy"
        );

        Set<String> uniqueWords = sentences.stream()
                .flatMap(sentence -> Arrays.stream(sentence.split(" ")))
                .map(word -> word.toLowerCase())
                .collect(Collectors.toCollection(() -> new LinkedHashSet<>()));

        System.out.println("Unique words: " + uniqueWords);
    }
}