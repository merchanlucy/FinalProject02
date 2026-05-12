package com.library.domain;

import com.library.interfaces.Reportable;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Admin extends User implements Reportable {
    public Admin(String name) {
        super(name);
        id = "A" + id;
    }

    public Admin(String id, String name, List<Item> borrowedItems) {
        super(id, name, borrowedItems);
    }

    @Override
    public boolean canBorrow(Item item) {
        return false;
    }

    @Override
    public Map<ItemStatus, List<Item>> generateReport(List<Item> items) {
        Map<ItemStatus, List<Item>> report = new HashMap<>();

        for (Item item : items) {
            if (!report.containsKey(item.getStatus())) {
                report.put(item.getStatus(), new ArrayList<>());
            }
            report.get(item.getStatus()).add(item);
        }

        return report;
    }
}
