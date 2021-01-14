package com.example.burrowwebapp.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
public class Component extends AbstractEntity {

    @Size(max = 250, message = "Description must be less than 250 characters")
    private String description;

    @ManyToOne
    private Device device;

    @NotNull(message = "Quantity must be greater than or equal to 1")
    @Min(value=1, message = "Quantity must be greater than or equal to 1")
    private Integer quantity;

    @NotNull(message = "Please enter a date")
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    @Temporal(TemporalType.DATE)
    private Date installDate;

    public Component(@NotBlank String name, @Size(max = 250, message = "Description must be less than 250 characters") String description,
                     Device device, @NotNull @Min(value=1) Integer quantity, @NotNull Date installDate) {
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

    public Integer getQuantity()
    {
        return quantity;
    }

    public void setQuantity(Integer quantity)
    {
        this.quantity = quantity;
    }

    public Date getInstallDate() {
        return installDate;
    }

    public void setInstallDate(Date installDate) {
        this.installDate = installDate;
    }

}
