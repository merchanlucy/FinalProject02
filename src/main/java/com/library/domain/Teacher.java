package com.library.domain;

import com.library.util.Constants;

import java.util.List;

public class Teacher extends User {
    public Teacher(String name) {
        super(name);
        id = "T" + id;
    }

    public Teacher(String id, String name, List<Item> borrowedItems) {
        super(id, name, borrowedItems);
    }

    @Override
    public boolean canBorrow(Item item) {
        return getBorrowedItems().size() < Constants.MAX_ITEMS_TEACHER;
    }
}
