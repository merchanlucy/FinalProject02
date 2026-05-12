package com.library.interfaces;

import com.library.domain.Item;
import com.library.domain.ItemStatus;

import java.util.List;
import java.util.Map;

public interface Reportable {
    /**
     * Groups library items by their current status.
     * @param items the list of library items
     * @return a map where each status has its own list of items
     */
    Map<ItemStatus, List<Item>> generateReport(List<Item> items);
}
