package com.example.burrowwebapp.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Entity
public class Component extends AbstractEntity {

    @ManyToOne
    private User user;

    @Size(max = 250, message = "Description must be less than 250 characters")
    private String description;

    @ManyToOne
    private Device device;

    @NotNull(message = "Quantity must be greater than or equal to 1")
    @Min(value=1, message = "Quantity must be greater than or equal to 1")
    private Integer quantity;

    @NotNull(message = "Please enter a date")
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private LocalDate installDate;

    @OneToOne(mappedBy = "component", cascade = CascadeType.ALL, orphanRemoval = true)
    private Notification notification;

    @NotNull
    @Min(value=1)
    @Max(value=3650)
    private long daysBetweenReplacements;

    public Component(@NotBlank String name, User user, @Size(max = 250, message = "Description must be less than 250 characters") String description,
                     Device device, @NotNull @Min(value=1) Integer quantity, @NotNull LocalDate installDate,
                     @NotNull @Min(value=1) @Max(value=3650)long daysBetweenReplacements) {
        this.setName(name);
        this.user = user;
        this.description = description;
        this.device = device;
        this.quantity = quantity;
        this.installDate = installDate;
        this.daysBetweenReplacements = daysBetweenReplacements;
        //this.notification = new Notification("It has been at least" + daysBetweenReplacements + "days since you replaced this" + name, installDate, daysBetweenReplacements);
    }

    public Component() {}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

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

    public LocalDate getInstallDate() {
        return installDate;
    }

    public void setInstallDate(LocalDate installDate) {
        this.installDate = installDate;
    }

    public Notification getNotification()
    {
        return notification;
    }

    public void setNotification(Notification notification)
    {
        this.notification = notification;
    }

    public long getDaysBetweenReplacements()
    {
        return daysBetweenReplacements;
    }

    public void setDaysBetweenReplacements(long daysBetweenReplacements)
    {
        this.daysBetweenReplacements = daysBetweenReplacements;
    }
}
