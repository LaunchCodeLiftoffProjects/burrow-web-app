package com.example.burrowwebapp.models;


import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Device extends AbstractEntity {

    private String description;

    @ManyToOne
    private Room room;

    public Device() {}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Room getRooms() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
