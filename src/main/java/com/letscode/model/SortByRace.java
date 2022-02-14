package com.letscode.model;

import java.util.Comparator;

public class SortByRace implements Comparator<Rebel> {
    @Override
    public int compare(Rebel r1, Rebel r2) {
        if(r1.getRace().compareTo(r2.getRace()) > 0){
            return 1;
        }
        return -1;
    }
}
