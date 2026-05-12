package com.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter
@ToString
@AllArgsConstructor
public abstract class Item {
    protected String id;
    @Setter protected String title;
    @Setter protected ItemStatus status;
    @Setter private static int nextId = 1;

    public Item(String title) {
        this.id = String.format("%04d", nextId++);
        this.title = title;
        this.status = ItemStatus.IN_STORE;
    }

    /**
     * checks if an item is available
     * @return true if the item is in store, false if item is not in store
     */
    public boolean isItemAvailable() {
        return status == ItemStatus.IN_STORE;
    }

    /**
     * marks an item as lost
     * @return true if item is marked as lost, false
     */
    public void markAsLost() {
        this.status = ItemStatus.LOST;
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
