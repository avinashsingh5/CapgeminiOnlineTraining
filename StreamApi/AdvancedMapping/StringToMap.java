package StreamApi.AdvancedMapping;
import java.util.*;
import java.util.stream.Collectors;

public class StringToMap {
    public static void main(String[] args) {

        List<String> words = Arrays.asList(
                "Java", "Streams", "Lambda", "Programming"
        );

        Map<String, Integer> map = words.stream()
                .collect(Collectors.toMap(
                        s -> s,
                        s -> s.length()
                ));

        System.out.println(map);
    }
}