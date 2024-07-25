package com.files;

public class Map {
    public enum MapSize {
        small, medium, large
    }

    String name;
    MapSize size;
    String message;

    public Map(String name, String size, String message) {
        this.name = name;
        this.size = MapSize.valueOf(size);
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public MapSize getSize() {
        return size;
    }

    public String getMessage() {
        return message;
    }
}
