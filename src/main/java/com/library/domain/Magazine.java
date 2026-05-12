package com.library.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class Magazine extends Item {
    private int issueNumber;
    private String publisher;

    public Magazine(String title, int issueNumber, String publisher) {
        super(title);
        id = "M" + id;
        this.issueNumber = issueNumber;
        this.publisher = publisher;
    }

    public Magazine(String id, String title, ItemStatus status, int issueNumber, String publisher) {
        super(id, title, status);
        this.issueNumber = issueNumber;
        this.publisher = publisher;
    }
}
