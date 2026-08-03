package StreamApi.ComplexSorting;

import java.util.List;

public class SortStringsByLength {

    public static void main(String[] args) {

        List<String> names = List.of("Banana","Apple","Kiwi","Mango","Orange","Grapes");

        List<String> sortedNames = names.stream()
                .sorted((firstName, secondName) -> {

                    if (firstName.length() != secondName.length()) {
                        return firstName.length() - secondName.length();
                    }

                    return firstName.compareTo(secondName);
                })
                .toList();

        System.out.println("Original list: " + names);
        System.out.println("Sorted list: " + sortedNames);
    }
}