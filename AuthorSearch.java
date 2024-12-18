package utils;
import services.*;

import java.util.ArrayList;
public  class AuthorSearch implements Researcher {
    @Override
    public int search(String keyword, ArrayList<Item> items) {
        int low = 0;
        int high = items.size() - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            String midAuthor = items.get(mid).getAuthor();
            if (midAuthor.equals(keyword)) {
                return mid;
            } else if (midAuthor.compareTo(keyword) < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }
}
