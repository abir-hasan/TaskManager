package com.example.abirhasan.finaltest.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Locale;

public class StringDateComparator implements Comparator<String> {

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());

    public int compare(String lhs, String rhs) {
        try {
            return dateFormat.parse(lhs).compareTo(dateFormat.parse(rhs));
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
