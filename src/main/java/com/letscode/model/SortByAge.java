package com.letscode.model;

import java.util.Comparator;

public class SortByAge implements Comparator<Rebel> {

    @Override
    public int compare(Rebel r1, Rebel r2) {
        if(r1.getAge() < r2.getAge()){
            return 1;
        }
        return -1;
    }
}
