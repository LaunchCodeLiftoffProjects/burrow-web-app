package com.example.burrowwebapp.models;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Property extends AbstractEntity {

    @OneToMany(mappedBy = "property")
    private final List<Room> rooms = new ArrayList<>();

    @NotBlank
    private String location;

    @NotBlank
    @Size(min = 1, max = 250, message = "Description must be between 1 and 250 characters")
    private String description;

    public Property(@NotBlank String name, @NotBlank String location,
                    @NotBlank @Size(min = 1, max = 250, message = "Description must be between 1 and 250 characters") String description)
    {
        this.setName(name);
        this.location = location;
        this.description = description;
    }

    public Property(){}

    public String getLocation()
    {
        return location;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }
}