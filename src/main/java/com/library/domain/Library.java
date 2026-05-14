package com.library.domain;

import com.library.exception.BorrowLimitExceededException;
import com.library.exception.ItemNotAvailableException;
import com.library.exception.ItemNotBorrowedException;
import com.library.exception.InvalidOperationException;
import com.library.service.CsvManager;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
            throw new InvalidOperationException(String.format("%s is already a user of the Library", user.getId()));
        }
        users.add(user);
    }

    /**
     * Allows a user to borrow an item.
     * @param user the user borrowing the item
     * @param item the item being borrowed
     */
    public void borrowItem(User user, Item item) {
        if (!item.isItemAvailable()) {
            throw new ItemNotAvailableException(item.getId());
        }

        if (!user.canBorrow(item)) {
            throw new BorrowLimitExceededException("This user cannot borrow this item.");
        }

        user.getBorrowedItems().add(item);
        item.setStatus(ItemStatus.BORROWED);
    }

    /**
     * Allows a user to return a borrowed item.
     * @param user the user returning the item
     * @param item the item being returned
     */
    public void returnItem(User user, Item item) {
        if (!user.getBorrowedItems().contains(item)) {
            throw new ItemNotBorrowedException("This user did not borrow item with ID: " + item.getId());
        }

        user.getBorrowedItems().remove(item);
        item.setStatus(ItemStatus.IN_STORE);
    }

    /**
     * Sorts the items by title
     */
    public void sortItemsByTitle() {
        items = items.stream()
                .sorted((item1, item2) -> item1.getTitle().compareToIgnoreCase(item2.getTitle()))
                .toList();
    }

    /**
     * Sorts the items by ID
     */
    public void sortItemsById() {
        items = items.stream()
                .sorted((item1, item2) -> item1.getId().compareToIgnoreCase(item2.getId()))
                .toList();
    }

    /**
     * Sorts the users by name
     */
    public void sortUsersByName() {
        users = users.stream()
                .sorted((user1, user2) -> user1.getName().compareToIgnoreCase(user2.getName()))
                .toList();
    }

    /**
     * Sorts the users by ID
     */
    public void sortUsersById() {
        users = users.stream()
                .sorted((user1, user2) -> user1.getId().compareToIgnoreCase(user2.getId()))
                .toList();
    }

    /**
     * searches items by title with stream
     * The search is case insensitive and returns only one copy per title.
     * @param title the title to search for
     * @return a list of items that contain the title
     */
    public List<Item> searchByTitleStream(String title) {
        Set<String> titlesAlreadyAdded = new HashSet<>();

        return items.stream()
                .filter(item -> (item.getTitle().toLowerCase().contains(title.toLowerCase())))
                .filter(item ->(titlesAlreadyAdded.add(item.getTitle().toLowerCase())))
                .toList();
    }

    /**
     * Searches books by author using streams.
     * The search is case insensitive and returns only one copy
     * @param author the author to search for
     * @return a list of books containing that author
     */
    public List<Item> searchByAuthorStream(String author) {
        Set<String> isbnsAlreadyAdded = new HashSet<>();
        String searchedAuthor = author.toLowerCase();

        return items.stream()
                .filter(item -> item instanceof Book)
                .map(item -> (Book) item)
                .filter(book -> book.getAuthor().toLowerCase().contains(searchedAuthor))
                .filter(book -> isbnsAlreadyAdded.add(book.getIsbn()))
                .map(book -> (Item) book)
                .toList();
    }

    /**
     * searches items by title with recursion
     * @param title the input title
     * @return a list of items that match the input title
     */
    public List<Item> searchByTitleRecursive(String title) {
        List<Item> results = new ArrayList<>();
        searchByTitleRecursive(title, 0, results);
        return results;
    }

    /**
     * Recursive method that checks items inside library
     * helper method
     * @param title the input title
     * @param idx the index of the items list
     * @param results the list of items that match the input title
     */
    private void searchByTitleRecursive(String title, int idx, List<Item> results) {
        if (idx >= items.size()) {
            return;
        }
        Item item = items.get(idx);
        if (item.getTitle().toLowerCase().contains(title.toLowerCase())) {
            results.add(item);
        }

        searchByTitleRecursive(title, idx + 1, results);
    }

    /**
     * Searches books by author using recursion
     * @param author the author or part of the author name to search for
     * @return a list of matching books
     */
    public List<Item> searchByAuthorRecursive(String author) {
        List<Item> results = new ArrayList<>();
        searchByAuthorRecursive(author, 0, results);
        return results;
    }

    /**
     * searches library books by author using recursion, private helper method
     * @param author the author to search
     * @param idx the index of the item in the list
     * @param results the list of books that contain that author
     */
    private void searchByAuthorRecursive(String author, int idx, List<Item> results) {
        if (idx >= items.size()) {
            return;
        }
        Item item = items.get(idx);

        if (item instanceof Book) {
            Book book = (Book) item;
            if (book.getAuthor().toLowerCase().contains(author.toLowerCase())) {
                results.add(book);
            }
        }

        searchByAuthorRecursive(author, idx + 1, results);
    }
}
