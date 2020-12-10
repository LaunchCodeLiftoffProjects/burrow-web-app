package com.example.burrowwebapp.models;


import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.List;

public class Device extends AbstractEntity {

    private String description;

    @ManyToOne
    private List<Room> rooms = new ArrayList<>();

    public Device() {}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }
}
