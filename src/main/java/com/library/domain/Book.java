package com.library.domain;

import com.library.exception.InvalidISBNException;
import com.library.util.Validation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class Book extends Item {
    private String isbn;
    private String author;
    private String genre;

    public Book(String title, String isbn, String author, String genre) {
        super(title);
        id = "B" + id;
        if (!Validation.isValidISBN(isbn)) {
            throw new InvalidISBNException("Invalid ISBN. ISBN must contain 13 digits.");
        }

        this.isbn = isbn;
        this.author = author;
        this.genre = genre;
    }

    //allArg constructor for TextIO
    public Book(String id, String title, ItemStatus status, String isbn, String author, String genre) {
        super(id, title, status);
        this.isbn = isbn;
        this.author = author;
        this.genre = genre;
    }
}
