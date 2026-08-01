package StreamApi.AdvancedMapping;
import java.util.*;

public class LongestStringLength {
    public static void main(String[] args) {

        List<String> words = Arrays.asList(
                "Java", "Streams", "Lambda", "Programming"
        );

        int longest = words.stream()
                .mapToInt(s -> s.length())
                .max()
                .orElse(0);

        System.out.println("Longest Length = " + longest);
    }
}