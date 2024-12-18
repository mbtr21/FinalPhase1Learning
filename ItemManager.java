package services;

import utils.TitleSearch;

import java.util.ArrayList;

public class ItemManager {
    private static ItemManager itemManager;

    private ItemManager() {}

    public static synchronized ItemManager getInstance() {
        if (itemManager == null) {
            itemManager = new ItemManager();
        }
        return itemManager;
    }

    public synchronized int borrowItem(String title, ArrayList<Item> items) {
        TitleSearch titleSearch = new TitleSearch();
        int index = titleSearch.search(title, items);
        if (index != -1) {
            Item item = items.get(index);
            if (item.getStatus().equals(Item.Status.Banned) || item.getStatus().equals(Item.Status.Borrowed)) {
                return 0;
            } else {
                item.setStatus(Item.Status.Borrowed);
                if (item.getType().equals(Item.Type.Book)) {
                    item.sendMessage("The Books "  + item.getTitle()  + "  is Borrowed");
                }
                return 1;
            }
        }
        return 0;
    }

    public synchronized int returnItem(String title, ArrayList<Item> items) {
        TitleSearch titleSearch = new TitleSearch();
        try {
            int index = titleSearch.search(title, items);
            Item item = items.get(index);
            item.setStatus(Item.Status.Exist);
            if (item.getType().equals(Item.Type.Book)) {
                item.sendMessage("The Books " + item.getTitle()  + "  is Returned");
            }
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }
}
