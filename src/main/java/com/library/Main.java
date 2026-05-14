package com.library;

import com.library.domain.Item;
import com.library.domain.User;
import com.library.service.CsvManager;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        CsvManager csvManager = new CsvManager();
        //test load items and users
        List<Item> items = csvManager.loadItems();
        for (Item item : items) {
            System.out.println(item);
        }

        List<User> users = csvManager.loadUsers(items);
        for (User user : users) {
            System.out.println(user);
        }

        // Test backup
        csvManager.backupItems(items);
        csvManager.backupUsers(users);
    }
}
