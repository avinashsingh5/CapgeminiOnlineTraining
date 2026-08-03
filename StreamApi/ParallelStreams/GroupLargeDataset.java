package StreamApi.ParallelStreams;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

class Transaction {

    private int transactionId;
    private String category;
    private double amount;

    public Transaction(
            int transactionId,
            String category,
            double amount
    ) {
        this.transactionId = transactionId;
        this.category = category;
        this.amount = amount;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }
}

public class GroupLargeDataset {

    public static void main(String[] args) {

        String[] categories = {
                "Food",
                "Electronics",
                "Clothing",
                "Books"
        };

        List<Transaction> transactions =
                IntStream.rangeClosed(1, 1_000_000)
                        .mapToObj(transactionNumber -> {

                            String category =
                                    categories[transactionNumber % categories.length];

                            double amount =
                                    (transactionNumber % 1000) + 100;

                            return new Transaction(
                                    transactionNumber,
                                    category,
                                    amount
                            );
                        })
                        .toList();

        Map<String, Double> totalAmountByCategory =
                transactions.parallelStream()
                        .collect(java.util.stream.Collectors.toConcurrentMap(
                                transaction -> transaction.getCategory(),
                                transaction -> transaction.getAmount(),
                                (firstAmount, secondAmount) ->
                                        firstAmount + secondAmount
                        ));

        totalAmountByCategory.forEach((category, totalAmount) ->
                System.out.println(
                        category + " total amount: " + totalAmount
                )
        );
    }
}