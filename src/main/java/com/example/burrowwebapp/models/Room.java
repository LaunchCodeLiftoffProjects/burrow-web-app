package com.example.burrowwebapp.models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Room extends AbstractEntity
{
    @ManyToOne
    @NotNull
    private Property property;

    public Room(){

    }
}
