package utils;
import java.util.ArrayList;
import services.*;


public class TitleSearch implements Researcher {
    @Override
    public int search(String keyword, ArrayList<Item> items) {
        int low = 0;
        int high = items.size() - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            String midTitle = items.get(mid).getTitle();
            if (midTitle.equals(keyword)) {
                return mid;
            } else if (midTitle.compareTo(keyword) < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }
}