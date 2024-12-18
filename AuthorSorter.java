package utils;
import services.*;
import java.util.ArrayList;
import java.util.Comparator;


public  class AuthorSorter implements ItemSorter {
    @Override
    public void sort(ArrayList<Item> items) {
        items.sort(Comparator.comparing(Item::getAuthor));
    }
}