package com.library.domain;

import com.library.util.Constants;

public class Student extends User {
    public Student(String name) {
        super(name);
    }

    @Override
    public boolean canBorrow(Item item) {
        return item instanceof Book
                && getBorrowedItems().size() < Constants.MAX_BOOKS_STUDENT;
    }
}
