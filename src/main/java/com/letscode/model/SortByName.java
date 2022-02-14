package com.letscode.model;

import java.util.Comparator;

public class SortByName implements Comparator<Rebel> {
    @Override
    public int compare(Rebel r1, Rebel r2) {
        if(r1.getName().compareTo(r2.getName()) > 0){
            return 1;
        }
        return -1;
    }
}
