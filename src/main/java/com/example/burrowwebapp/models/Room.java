package com.example.burrowwebapp.models;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Room extends AbstractEntity {

    @ManyToOne
    private Property property;

    @OneToMany (mappedBy = "room")
    private List<Device> devices = new ArrayList<>();

    public Room(){ }

    public Room(Property aProperty, List<Device> someDevices) {
        super();
        this.property = aProperty;
        this.devices = someDevices;
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



