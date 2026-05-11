package com.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Setter
@Getter
@ToString
@AllArgsConstructor
public abstract class Item {
    private String id;
    private String title;
    private ItemStatus status;
    private static int nextId = 1;

    public Item(String title) {
        this.id = String.format("%04d", nextId++);
        this.title = title;
        this.status = ItemStatus.IN_STORE;
    }

    public boolean isItemAvailable() {
        return status == ItemStatus.IN_STORE;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(id, item.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, status);
    }
}
