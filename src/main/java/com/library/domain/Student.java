package com.library.domain;

import com.library.util.Constants;

import java.util.List;

public class Student extends User {
    public Student(String name) {
        super(name);
        id = "S" + id;
    }

    public Student(String id, String name, List<Item> borrowedItems) {
        super(id, name, borrowedItems);
    }

    @Override
    public boolean canBorrow(Item item) {
        return item instanceof Book
                && getBorrowedItems().size() < Constants.MAX_BOOKS_STUDENT;
    }
}
