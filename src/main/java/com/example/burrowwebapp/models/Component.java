package com.example.burrowwebapp.models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class Component extends AbstractEntity {

    @Size(max = 250, message = "Description too long!")
    private String description;

    @ManyToOne
    private Device device;

    public Component(@NotBlank String name, @Size(max = 250, message = "Description too long!") String description,
                     Device device) {
        this.setName(name);
        this.description = description;
        this.device = device;
    }

    public Component() {}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }
}
