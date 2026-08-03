package StreamApi.ComplexSorting;


import java.util.List;

public class SortNumbersByAbsoluteValue {

    public static void main(String[] args) {

        List<Integer> numbers = List.of(-10,5,-2,8, -1, 3);

        List<Integer> sortedNumbers = numbers.stream()
                .sorted((firstNumber, secondNumber) -> {

                    int firstAbsoluteValue = Math.abs(firstNumber);
                    int secondAbsoluteValue = Math.abs(secondNumber);

                    return firstAbsoluteValue - secondAbsoluteValue;
                })
                .toList();

        System.out.println("Original list: " + numbers);
        System.out.println("Sorted list: " + sortedNumbers);
    }
}