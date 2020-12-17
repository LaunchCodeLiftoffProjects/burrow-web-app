package com.example.burrowwebapp.models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;

@Entity
public class Component extends AbstractEntity {

    @ManyToOne
    private Device device;

    private int quantity;

    @NotBlank
    private String type;

    @Size(max = 250, message = "Description must less than 250 characters")
    private String description;

    public Component(Device device, int quantity, @NotBlank String type, @Size(max = 250, message = "Description must less than 250 characters") String description) {
        this.device = device;
        this.quantity = quantity;
        this.type = type;
        this.description = description;
    }

    public Component() {
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
