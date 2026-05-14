package com.library.domain;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
public abstract class User {
    protected String id;
    @Setter protected String name;
    protected List<Item> borrowedItems;
    @Setter private static int nextId = 1;

    public User(String name) {
        this.id = String.format("%04d", nextId++);
        this.name = name;
        this.borrowedItems = new ArrayList<>();
    }

    /**
     * checks if the user is allowed to borrow an item
     * @param item the item to be borrowed
     * @return true if the user can borrow item, false if they cant
     */
    public abstract boolean canBorrow(Item item);
}
