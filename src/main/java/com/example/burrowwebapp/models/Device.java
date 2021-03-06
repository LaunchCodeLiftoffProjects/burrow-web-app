package com.example.burrowwebapp.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Device extends AbstractEntity {

    @ManyToOne
    private User user;

    @ManyToOne
    private Room room;

    @OneToMany(mappedBy = "device", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Component> components = new ArrayList<>();

    @Size(max = 250, message = "Description must be less than 250 characters")
    private String description;

    public Device (@NotBlank String name, User user, Room room, @Size(max = 250, message = "Description must be less than 250 characters") String description)
    {
        this.setName(name);
        this.user = user;
        this.room = room;
        this.description = description;
    }

    public Device() {}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

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

    public List<Component> getComponents() {
        return components;
    }
}
