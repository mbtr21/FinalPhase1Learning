package services;

import data.Library;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FileDataLoader implements DataLoader {
    private final String filePath;

    public FileDataLoader(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void loadData(ArrayList<Item> items) {
        if (filePath == null || filePath.trim().isEmpty()) {
            System.err.println("No file path provided.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                Item item = parseLine(line, lineNumber);
                if (item != null) {
                    items.add(item);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the file \"" + filePath + "\": " + e.getMessage());
        }
    }

    private Item parseLine(String line, int lineNumber) {
        String[] parts = line.split(",");
        if (parts.length < 6) {
            System.err.println("Line " + lineNumber + " has insufficient data.");
            return null;
        }

        String type = safeTrim(parts[0]);
        String title = safeTrim(parts[1]);
        String author = safeTrim(parts[2]);
        Date borrowed = parseDate(safeTrim(parts[3]));
        Date returned = parseDate(safeTrim(parts[4]));

        if (type.isEmpty() || title.isEmpty() || author.isEmpty()) {
            System.err.println("Line " + lineNumber + " missing mandatory fields.");
            return null;
        }

        Item.Status status;
        try {
            status = Item.Status.valueOf(safeTrim(parts[5]));
        } catch (IllegalArgumentException e) {
            System.err.println("Line " + lineNumber + ": Invalid status value.");
            return null;
        }

        try {
            if (type.equalsIgnoreCase("Repository.Book")) {
                if (parts.length < 8) {
                    System.err.println("Line " + lineNumber + ": Repository.Book entry missing fields.");
                    return null;
                }
                Integer publishedYear = Integer.valueOf(safeTrim(parts[6]));
                String description = safeTrim(parts[7]);
                return new Book(title, author, publishedYear, borrowed, returned, status, Item.Type.Book);

            } else if (type.equalsIgnoreCase("Repository.Magazine")) {
                if (parts.length < 7) {
                    System.err.println("Line " + lineNumber + ": Repository.Magazine entry missing genre.");
                    return null;
                }
                String genre = safeTrim(parts[6]);
                return new Magazine(title, author, borrowed, returned, status, Item.Type.Magazine, genre);

            } else if (type.equalsIgnoreCase("Repository.Reference")) {
                if (parts.length < 7) {
                    System.err.println("Line " + lineNumber + ": Repository.Reference entry missing description.");
                    return null;
                }
                String description = safeTrim(parts[6]);
                return new Reference(title, author, description, borrowed, returned, status, Item.Type.Reference);

            } else {
                System.err.println("Line " + lineNumber + ": Unknown item type \"" + type + "\".");
            }
        } catch (NumberFormatException e) {
            System.err.println("Line " + lineNumber + ": Number parsing error.");
        } catch (Exception e) {
            System.err.println("Line " + lineNumber + ": Unexpected error parsing line: " + e.getMessage());
        }

        return null;
    }

    private Date parseDate(String dateStr) {
        if (dateStr.isEmpty()) {
            return null;
        }
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
        } catch (Exception e) {
            return null;
        }
    }

    private String safeTrim(String s) {
        return s == null ? "" : s.trim();
    }
}
