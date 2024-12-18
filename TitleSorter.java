package utils;
import services.*;
import java.util.ArrayList;
import java.util.Comparator;



public  class TitleSorter implements ItemSorter {
    @Override
    public void sort(ArrayList<Item> items) {
        items.sort(Comparator.comparing(Item::getTitle));
    }
}

