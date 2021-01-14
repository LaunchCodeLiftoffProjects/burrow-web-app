package com.example.burrowwebapp.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Property extends AbstractEntity {

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Room> rooms = new ArrayList<>();

    @NotBlank(message = "Location may not be blank")
    private String location;

    @NotBlank(message = "Description may not be blank")
    @Size(max = 250, message = "Description must be less than 250 characters")
    private String description;

    public Property(@NotBlank String name, @NotBlank String location,
                    @NotBlank @Size(max = 250, message = "Description must be less than 250 characters") String description, List<Room> rooms)
    {
        this.setName(name);
        this.location = location;
        this.description = description;
        this.rooms = rooms;
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

    public List<Room> getRooms()
    {
        return rooms;
    }

    public void addRoom(Room newRoom){
        this.rooms.add(newRoom);
    }
}
