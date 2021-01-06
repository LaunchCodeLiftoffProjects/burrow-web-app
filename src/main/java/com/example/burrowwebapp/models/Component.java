package com.example.burrowwebapp.models;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
public class Component extends AbstractEntity {

    @Size(max = 250, message = "Description must be less than 250 characters")
    private String description;

    @ManyToOne
    private Device device;

    @Min(value=1, message = "Quantity must be greater than or equal to 1")
    private int quantity;

    @Temporal(TemporalType.DATE)
    @Column(name = "install_date")
    private Date installDate;

    public Component(@NotBlank String name, @Size(max = 250, message = "Description must be less than 250 characters") String description,
                     Device device, @Min(value=1) int quantity, Date installDate) {
        this.setName(name);
        this.description = description;
        this.device = device;
        this.quantity = quantity;
        this.installDate = installDate;
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

    public int getQuantity()
    {
        return quantity;
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }

    public Date getInstallDate() {
        return installDate;
    }

    public void setInstallDate(Date installDate) {
        this.installDate = installDate;
    }

    @Override
    public Property getProperty() {
        return null;
    }
}
