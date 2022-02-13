package com.letscode.model;

public enum Race {
    HUMANO("Humano"),
    GREE("Gree"),
    RAKATA("Vapor");

    private String description;

    Race(String description) {this.description = description;}

    public String getDescription() { return description; }
}
