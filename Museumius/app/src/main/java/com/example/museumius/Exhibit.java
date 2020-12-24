package com.example.museumius;

import java.io.Serializable;

public class Exhibit implements Serializable {
    private String name;
    private String description;
    private String img;

    public Exhibit(String name, String description, String img) {
        this.name = name;
        this.description = description;
        this.img = img;
    }

    public String getImg() {
        return img;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }
}