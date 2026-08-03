package StreamApi.RealWorldScenarios;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class Book {

    private String title;
    private String author;
    private int publishedYear;
    private String genre;

    public Book(String title, String author, int publishedYear, String genre) {
        this.title = title;
        this.author = author;
        this.publishedYear = publishedYear;
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getPublishedYear() {
        return publishedYear;
    }

    public String getGenre() {
        return genre;
    }
}

public class LibrarySystemAnalysis {

    public static void main(String[] args) {

        List<Book> books = List.of(
                new Book("Java Basics", "James", 2020, "Programming"),
                new Book("Spring Boot", "James", 2022, "Programming"),
                new Book("The Lost City", "Robert", 2018, "Fiction"),
                new Book("Advanced Java", "David", 2023, "Programming"),
                new Book("Hidden Truth", "Robert", 2021, "Mystery"),
                new Book("Dark Night", "Robert", 2024, "Mystery")
        );

        Map<String, Long> booksByGenre = books.stream()
                .collect(Collectors.groupingBy(
                        book -> book.getGenre(),
                        Collectors.counting()
                ));

        Map<String, Long> booksByAuthor = books.stream()
                .collect(Collectors.groupingBy(
                        book -> book.getAuthor(),
                        Collectors.counting()
                ));

        Map.Entry<String, Long> mostPublishedGenre = booksByGenre.entrySet()
                .stream()
                .max((firstGenre, secondGenre) ->
                        Long.compare(
                                firstGenre.getValue(),
                                secondGenre.getValue()
                        )
                )
                .orElse(null);

        Map.Entry<String, Long> authorWithMostBooks = booksByAuthor.entrySet()
                .stream()
                .max((firstAuthor, secondAuthor) ->
                        Long.compare(
                                firstAuthor.getValue(),
                                secondAuthor.getValue()
                        )
                )
                .orElse(null);

        System.out.println("Books by genre: " + booksByGenre);
        System.out.println("Books by author: " + booksByAuthor);

        if (mostPublishedGenre != null) {
            System.out.println(
                    "Most published genre: "
                            + mostPublishedGenre.getKey()
                            + " with "
                            + mostPublishedGenre.getValue()
                            + " books"
            );
        }

        if (authorWithMostBooks != null) {
            System.out.println(
                    "Author with most books: "
                            + authorWithMostBooks.getKey()
                            + " with "
                            + authorWithMostBooks.getValue()
                            + " books"
            );
        }
    }
}