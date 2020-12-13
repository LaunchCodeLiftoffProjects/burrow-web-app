package com.example.burrowwebapp.models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Device extends AbstractEntity{

    @ManyToOne
    @NotNull
    private Room room;

    public Device() {

    }

}
