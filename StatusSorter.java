package utils;
import services.*;
import java.util.ArrayList;
import java.util.Comparator;

public class StatusSorter implements ItemSorter {
    @Override
    public void sort(ArrayList<Item> items) {
        items.sort(Comparator.comparing(Item::getStatus));
    }
}