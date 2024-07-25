package com.files;

public class Penalty {
    String name;
    String message;

    public Penalty(String name, String message) {
        this.name = name;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }
}
