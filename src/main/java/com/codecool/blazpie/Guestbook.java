package com.codecool.blazpie;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

public class Guestbook {

    DatabaseAccess dao;

    public Guestbook() {
        try {
            dao = new DatabaseAccess("gb");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public List<List<String>> getEntries() {
        List<List<String>> result;
        try {
            result = dao.getEntries();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            result = new ArrayList<>();
            result.add(new ArrayList<>());
        }
        return result;
    }

    public List<List<String>> addEntry(Map<String, String> inputs) {
        List<List<String>> result;
        try {
            inputs.put("timestamp", LocalDateTime.now().toString());
            dao.addRecord(inputs);
            result = getEntries();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            result = null;
        }
        return result;
    }
}
