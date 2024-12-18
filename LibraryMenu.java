package ui;

import services.*;
import utils.*;
import data.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;


class LibraryMenu {
    private static final Library library = new Library();
    private static ItemSorter sorter;

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            library.add(new Book("Java Programming", "John Doe", 2020, null, null, Item.Status.Exist, Item.Type.Book));
            library.add(new Magazine("Tech Today", "Jane Smith", null, null, Item.Status.Exist, Item.Type.Magazine, "Technology"));

            int choice;
            while (true) {
                displayMenu();
                if (!scanner.hasNextInt()) {
                    System.out.println("Invalid input, please enter a number.");
                    scanner.nextLine();
                    continue;
                }

                choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        addItem(scanner);
                        break;
                    case 2:
                        removeItem(scanner);
                        break;
                    case 3:
                        searchItem(scanner);
                        break;
                    case 4:
                        sortItems(scanner);
                        break;
                    case 5:
                        borrowItem(scanner);
                        break;
                    case 6:
                        returnItem(scanner);
                        break;
                    case 7:
                        loadDataFromFile(scanner);
                        break;
                    case 8:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid choice, please try again.");
                }
            }
        }
    }

    private static void displayMenu() {
        System.out.println("\ndata.Library Menu:");
        System.out.println("1. Add item");
        System.out.println("2. Remove item");
        System.out.println("3. Search item");
        System.out.println("4. Sort items");
        System.out.println("5. Borrow item");
        System.out.println("6. Return item");
        System.out.println("7. Load data from file");
        System.out.println("8. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void addItem(Scanner scanner) {
        System.out.print("Enter the type of item (Repository.Book, Repository.Magazine, Repository.Reference): ");
        String type = scanner.nextLine().trim();
        System.out.print("Enter the title: ");
        String title = scanner.nextLine().trim();
        System.out.print("Enter the author: ");
        String author = scanner.nextLine().trim();

        Item item = null;
        if (type.equalsIgnoreCase("Repository.Book")) {
            System.out.print("Enter the published year (YYYY): ");
            String yearStr = scanner.nextLine().trim();
            if (!yearStr.matches("\\d{4}")) {
                System.out.println("Invalid year format. Please enter a 4-digit year (e.g., 2020).");
                return;
            }
            int year = Integer.parseInt(yearStr);
            item = new Book(title, author, year, null, null, Item.Status.Exist, Item.Type.Book);
        } else if (type.equalsIgnoreCase("Repository.Magazine")) {
            System.out.print("Enter the genre: ");
            String genre = scanner.nextLine().trim();
            item = new Magazine(title, author, null, null, Item.Status.Exist, Item.Type.Magazine, genre);
        } else if (type.equalsIgnoreCase("Repository.Reference")) {
            System.out.print("Enter the description: ");
            String description = scanner.nextLine().trim();
            item = new Reference(title, author, description, null, null, Item.Status.Exist, Item.Type.Reference);
        }

        if (item != null) {
            library.add(item);
            System.out.println("Repository.Item added successfully.");
        } else {
            System.out.println("Invalid item type.");
        }
    }

    private static void removeItem(Scanner scanner) {
        System.out.print("Enter the title of the item to remove: ");
        String title = scanner.nextLine().trim();
        TitleSearch researcher = new TitleSearch();
        library.search(title, (Researcher) researcher);
        Item item = null;

        for (Item i : library.itemList) {
            if (i.getTitle().equals(title)) {
                item = i;
                break;
            }
        }

        if (item != null) {
            library.remove(title, (Researcher) researcher);
            System.out.println("Repository.Item removed successfully.");
        } else {
            System.out.println("Repository.Item not found.");
        }
    }

    private static void searchItem(Scanner scanner) {
        System.out.print("Enter the search method (author/title): ");
        String method = scanner.nextLine().trim();
        System.out.print("Enter the search keyword: ");
        String keyword = scanner.nextLine().trim();

        Researcher researcher = null;
        if (method.equalsIgnoreCase("author")) {
            researcher = new   AuthorSearch();
        } else if (method.equalsIgnoreCase("title")) {
            researcher = new TitleSearch();
        }

        if (researcher != null) {
            int resultIndex = researcher.search(keyword, library.itemList);
            if (resultIndex != -1) {
                Item item = library.itemList.get(resultIndex);
                System.out.println("Found item: " + item.getTitle() + " by " + item.getAuthor());
            } else {
                System.out.println("Repository.Item not found.");
            }
        } else {
            System.out.println("Invalid search method.");
        }
    }

    private static   void sortItems(Scanner scanner) {
        System.out.print("Enter the sort method (author/title/status): ");
        String method = scanner.nextLine().trim();

        if (method.equalsIgnoreCase("author")) {
             sorter = new AuthorSorter();
        } else if (method.equalsIgnoreCase("title")) {
             sorter = new TitleSorter();
        } else if (method.equalsIgnoreCase("status")) {
             sorter = new StatusSorter();
        }

        if (sorter != null) {
            library.sort(library.itemList, sorter);
            System.out.println("Items sorted by " + method + ".");
        } else {
            System.out.println("Invalid sort method.");
        }
    }

    private static void borrowItem(Scanner scanner) {
        System.out.print("Enter the title of the item to borrow: ");
        String title = scanner.nextLine().trim();
        library.borrowItem(title);
    }

    private static void returnItem(Scanner scanner) {
        System.out.print("Enter the title of the item to return: ");
        String title = scanner.nextLine().trim();
        library.returnItem(title);
        System.out.println("Repository.Item returned successfully.");
    }

    private static void loadDataFromFile(Scanner scanner) {
        System.out.print("Enter file path: ");
        String filePath = scanner.nextLine().trim();
        if (filePath.isEmpty() || !Files.exists(Paths.get(filePath))) {
            System.err.println("Invalid or non-existent file path.");
            return;
        }
        DataLoader dataLoader = new FileDataLoader(filePath);
        dataLoader.loadData(library.itemList);
        System.out.println("Data loaded successfully.");
    }
}
