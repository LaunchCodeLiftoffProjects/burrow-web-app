package com.example.burrowwebapp.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Room extends AbstractEntity {

    @ManyToOne
    private User user;

    @ManyToOne
    private Property property;

    @OneToMany (mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Device> devices = new ArrayList<>();

    public Room(){ }

    public Room(@NotBlank String name, User user, Property property) {
        this.setName(name);
        this.user = user;
        this.property = property;
    }

    public Room(String s, Property property) {
        super();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }

    //testing dan's theory
}



