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

    public Item(String id, String title) {
        this.id = id;
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
        return Objects.equals(id, item.id) && Objects.equals(title, item.title) && Objects.equals(status, item.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, status);
    }
}
