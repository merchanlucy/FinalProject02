package com.library.service;

import com.library.domain.*;
import com.library.util.Constants;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CsvManager {

    /**
     * Backs up all items into the items CSV file.
     */
    public void backupItems(List<Item> items) {
        File file = new File(Constants.ITEMS_CSV_PATH);

        try (FileWriter fileWriter = new FileWriter(file, true)) {
            if (!file.exists()) {
                fileWriter.write("id,status,info1,info2,info3,info4\n");
            }

            for (Item item : items) {
                if (item instanceof Book) {
                    Book book = (Book) item;

                    fileWriter.write(String.format(
                            "%s,%s,%s,%s,%s,%s\n",
                            book.getId(),
                            book.getStatus(),
                            book.getIsbn(),
                            book.getTitle(),
                            book.getAuthor(),
                            book.getGenre()
                    ));

                } else if (item instanceof DVD) {
                    DVD dvd = (DVD) item;

                    fileWriter.write(String.format(
                            "%s,%s,%s,%s,%d\n",
                            dvd.getId(),
                            dvd.getStatus(),
                            dvd.getTitle(),
                            dvd.getDirector(),
                            dvd.getDurationMinutes()
                    ));

                } else if (item instanceof Magazine) {
                    Magazine magazine = (Magazine) item;

                    fileWriter.write(String.format(
                            "%s,%s,%s,%d,%s\n",
                            magazine.getId(),
                            magazine.getStatus(),
                            magazine.getTitle(),
                            magazine.getIssueNumber(),
                            magazine.getPublisher()
                    ));
                }
            }

        } catch (IOException e) {
            System.out.println("Writing item data file failed");
        }
    }

    /**
     * Load all items from a CSV file
     */
    public void loadItems() {
        File file = new File(Constants.ITEMS_CSV_PATH);
        List<Item> items = new ArrayList<>();

        try (Scanner scanner = new Scanner(file)) {
            scanner.nextLine();

            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] data = line.split(",");
                String id = data[0];
                ItemStatus status = ItemStatus.valueOf(data[2]);

                Item item = switch (id.charAt(0)) {
                    case 'B' -> new Book(
                            id,
                            data[1], //title
                            status,
                            data[3], //isbn
                            data[4], //author
                            data[5] //genre
                    );

                    case 'D' -> new DVD(
                            id,
                            data[1], //title
                            status,
                            data[3], //director
                            Integer.parseInt(data[4]) //durationMinutes
                    );

                    case 'M' -> new Magazine(
                            id,
                            data[1],// title
                            status,
                            Integer.parseInt(data[3]), //issueNumber
                            data[4] // publisher
                    );
                    default -> null;
                };

                if (item != null) {
                    items.add(item);
                }
            }

            Item.setNextId(items.size() + 1);

        } catch (FileNotFoundException e) {
            System.out.println("The item data file does not exist, initialize the item list");
        }
    }
}


