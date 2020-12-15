package com.example.burrowwebapp.models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;

@Entity
public class Device extends AbstractEntity {

    @ManyToOne
    private Room room;

//    @OneToMany(mappedBy = "device");
//    private final List<Component> components = new ArrayList<>();

    @Size(max = 250, message = "Description must less than 250 characters")
    private String description;

    public Device (@NotBlank String name, Room room, @Size(max = 250, message = "Description must less than 250 characters") String description)
    {
        this.setName(name);
        this.room = room;
        this.description = description;
    }

    public Device() {}

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
