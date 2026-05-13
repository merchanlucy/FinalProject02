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
     * @param items list of items to be backed up
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
     * @return a list of items from csv file
     */
    public List<Item> loadItems() {
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
        return items;
    }

    /**
     * Backs up all users into the users CSV file.
     * @param users list of users to be backed up
     */
    public void backupUsers(List<User> users) {
        File file = new File(Constants.USERS_CSV_PATH);

        try (FileWriter fileWriter = new FileWriter(file, true)) {
            fileWriter.write("id,name,borrowedItems\n");

            for (User user : users) {
                fileWriter.write(String.format("%s,%s", user.getId(), user.getName()));

                for (Item item : user.getBorrowedItems()) {
                    fileWriter.write("," + item.getId());
                }

                fileWriter.write("\n");
            }

        } catch (IOException e) {
            System.out.println("Writing user data file failed.");
        }
    }

    /**
     * load all users into the system
     * @param items list of items in the system
     * @return a list of users
     */
    public List<User> loadUsers(List<Item> items) {
        File file = new File(Constants.USERS_CSV_PATH);
        List<User> users = new ArrayList<>();

        try (Scanner scanner = new Scanner(file)) {
            scanner.nextLine();

            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] data = line.split(",");

                String id = data[0];
                String name = data[1];

                List<Item> borrowedItems = new ArrayList<>();

                for (int i = 2; i < data.length; i++) {
                    String borrowedItemId = data[i];

                    for (Item item : items) {
                        if (item.getId().equals(borrowedItemId)) {
                            borrowedItems.add(item);
                            item.setStatus(ItemStatus.BORROWED);
                        }
                    }
                }

                User user = switch (id.charAt(0)) {
                    case 'S' -> new Student(id, name, borrowedItems);
                    case 'T' -> new Teacher(id, name, borrowedItems);
                    case 'A' -> new Admin(id, name, borrowedItems);
                    default -> null;
                };

                if (user != null) {
                    users.add(user);
                }
            }

            User.setNextId(users.size() + 1);

        } catch (FileNotFoundException e) {
            System.out.println("The users file does not exist.");
        }
        return users;
    }
}


