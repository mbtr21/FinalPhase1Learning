package org.example;

import data.Library;
import services.Book;
import services.Item;
import services.User;

import java.util.Date;

public class Main {
    public static void main(String[] args) {
        Library library = new Library();

        for (int i = 1; i <= 20; i++) {
            String title = "BOOK " + i;
            String author = "Author " + i;
            Integer publishedYear = 2000 + i;
            Date borrowed = null;
            Date returned = null;
            Item.Status status = Item.Status.Exist;
            Item.Type type = Item.Type.Book;
            Book book = new Book(title, author, publishedYear, borrowed, returned, status, type);
            library.add(book);
        }

        User observer = new User("Observer1");
        if (!library.itemList.isEmpty()) {
            Book observedBook = (Book) library.itemList.get(0);
            observedBook.addObserver(observer);
        }

        String firstBookTitle = library.itemList.get(0).getTitle();
        if (library.borrowItem(firstBookTitle) == 1) {
            System.out.println("Successfully borrowed: " + firstBookTitle);
        }

        if (library.returnItem(firstBookTitle) == 1) {
            System.out.println("Successfully returned: " + firstBookTitle);
        }

        library.lowerCase.apply(library.itemList);

        for (Item item : library.itemList) {
            if (item instanceof Book) {
                ((Book) item).display();
            }

        }

        library.printDetails.accept(library.itemList);
    }
}
