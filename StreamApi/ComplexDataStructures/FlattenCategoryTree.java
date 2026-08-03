package StreamApi.ComplexDataStructures;

import java.util.ArrayList;
import java.util.List;

class Category {

    private String categoryName;
    private List<Category> subcategories;

    public Category(
            String categoryName,
            List<Category> subcategories
    ) {
        this.categoryName = categoryName;
        this.subcategories = subcategories;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public List<Category> getSubcategories() {
        return subcategories;
    }
}

public class FlattenCategoryTree {

    public static void main(String[] args) {

        Category android = new Category(
                "Android",
                List.of()
        );

        Category iphone = new Category(
                "iPhone",
                List.of()
        );

        Category mobile = new Category(
                "Mobile",
                List.of(android, iphone)
        );

        Category laptop = new Category(
                "Laptop",
                List.of()
        );

        Category desktop = new Category(
                "Desktop",
                List.of()
        );

        Category computer = new Category(
                "Computer",
                List.of(laptop, desktop)
        );

        Category electronics = new Category(
                "Electronics",
                List.of(mobile, computer)
        );

        List<String> allCategoryNames =
                flattenCategories(electronics);

        System.out.println(
                "All categories: " + allCategoryNames
        );
    }

    private static List<String> flattenCategories(
            Category category
    ) {

        List<String> categoryNames = new ArrayList<>();

        categoryNames.add(category.getCategoryName());

        category.getSubcategories()
                .forEach(subcategory -> {

                    List<String> childCategoryNames =
                            flattenCategories(subcategory);

                    categoryNames.addAll(childCategoryNames);
                });

        return categoryNames;
    }
}