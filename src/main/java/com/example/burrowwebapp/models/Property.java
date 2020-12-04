package com.example.burrowwebapp.models;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Property extends AbstractEntity {

    private final List<Room> rooms = new ArrayList<>();

    public Property(@NotBlank String name){
        this.setName(name);
    }

    public Property(){}

}
