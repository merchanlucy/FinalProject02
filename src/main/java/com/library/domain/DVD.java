package com.library.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class DVD extends Item {
    private String director;
    private int durationMinutes;

    public DVD(String title, String director, int durationMinutes) {
        super(title);
        this.director = director;
        this.durationMinutes = durationMinutes;
    }

    public DVD(String id, String title, ItemStatus status, String director, int durationMinutes) {
        super(id, title, status);
        this.director = director;
        this.durationMinutes = durationMinutes;
    }
}
