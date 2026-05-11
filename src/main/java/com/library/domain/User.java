package com.library.domain;

import com.library.exception.BorrowLimitExceededException;
import com.library.exception.ItemNotBorrowedException;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
public abstract class User {
    private String id;
    private String name;
    private List<Item> borrowedItems = new ArrayList<>();
    private static int nextId = 1;

    public User(String name) {
        this.id = String.format("%04d", nextId++);
        this.name = name;
    }

    /**
     * checks if the user is allowed to borrow an item
     * @param item the item to be borrowed
     * @return true if the user can borrow item, false if they cant
     */
    public abstract boolean canBorrow(Item item);

    /**
     * adds item to list of borrowed items
     * @param item item to be borrowed
     */
    public void borrowItem(Item item) {
        if (!canBorrow(item)) {
            throw new BorrowLimitExceededException("This user cannot borrow this item.");
        }

        borrowedItems.add(item);
        item.setStatus(ItemStatus.BORROWED);
    }

    /**
     * returns item to the library
     * @param item item to be returned
     */
    public void returnItem(Item item) {
        if (!borrowedItems.contains(item)) {
            throw new ItemNotBorrowedException("This user did not borrow this item.");
        }

        borrowedItems.remove(item);
        item.setStatus(ItemStatus.IN_STORE);
    }
}
