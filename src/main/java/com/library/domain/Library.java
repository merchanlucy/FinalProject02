package com.library.domain;

import com.library.exception.ItemNotAvailableException;
import com.library.exception.ItemNotBorrowedException;
import com.library.exception.InvalidOperationException;
import com.library.service.CsvManager;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@AllArgsConstructor //all args constructor for test
public class Library {

    private List<Item> items;
    private List<User> users;
    private CsvManager csvManager;

    public Library() {
        this.csvManager = new CsvManager();
        this.items = csvManager.loadItems();
        this.users = csvManager.loadUsers(items);
    }

    /**
     * Allows an admin to back up the current users and items into CSV files.
     * @param user the user trying to back up the data
     */
    public void backupData(User user) {
        if (!(user instanceof Admin)) {
            throw new InvalidOperationException("Only an admin can back up the library data.");
        }
        csvManager.backupItems(items);
        csvManager.backupUsers(users);
    }

    /**
     * Adds an item to the library.
     * @param item the item to add
     */
    public void addItem(Item item) {
        if (items.contains(item)) {
            throw new InvalidOperationException(String.format("Library already contains %s", item.getId()));
        }
        items.add(item);
    }

    /**
     * Adds a user to the library.
     * @param user the user to add
     */
    public void addUser(User user) {
        if (users.contains(user)) {
            throw new InvalidOperationException(String.format( "%s is already a user of the Library", user.getId()));
        }
        users.add(user);
    }

    /**
     * Allows a user to borrow an item.
     *
     * @param user the user borrowing the item
     * @param item the item being borrowed
     */
    public void borrowItem(User user, Item item) {
        if (!item.isItemAvailable()) {
            throw new ItemNotAvailableException(item.getId());
        }

        user.borrowItem(item);
        item.setStatus(ItemStatus.BORROWED);
    }

    /**
     * Allows a user to return a borrowed item.
     * @param user the user returning the item
     * @param item the item being returned
     */
    public void returnItem(User user, Item item) {
        if (!user.getBorrowedItems().contains(item)) {
            throw new ItemNotBorrowedException("This user did not borrow item with ID:" + item.getId());
        }

        user.returnItem(item);
        item.setStatus(ItemStatus.IN_STORE);
    }
}