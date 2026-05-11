package com.library.domain;

import com.library.util.Constants;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Student extends User {
    public Student(String name) {
        super(name);
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
