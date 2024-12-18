package services;

public class ItemFactory {
    public static Item createItem(Item.Type type, String title, String author) {
        switch (type) {
            case Book:
                return new Book(title, author, 2023, null, null, null, type);
            case Magazine:
                return new Magazine(title, author, null, null, null, type, "Default Genre");
            case Reference:
                return new Reference(title, author, "Default Description", null, null, null, type);
            default:
                throw new IllegalArgumentException("Invalid item type.");
        }
    }
}
