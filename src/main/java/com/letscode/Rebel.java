package com.letscode;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Rebel {
    private String name;
    private int age;
    private Race race;
}