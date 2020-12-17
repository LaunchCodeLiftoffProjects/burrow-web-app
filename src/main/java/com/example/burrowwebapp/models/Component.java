package com.example.burrowwebapp.models;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Component extends AbstractEntity {

    @Size(max = 250, message = "Description too long!")
    private String description;

    @ManyToMany(mappedBy = "components")
    private final List<Device> devices = new ArrayList<>();

    public Component(String name, @Size(max = 250, message = "Description too long!") String description) {
        this.setName(name);
        this.description = description;
    }

    public Component() {}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Device> getDevices() {
        return devices;
    }
}
