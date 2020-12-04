package com.example.burrowwebapp.models;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

public class Property
{
    @Id
    @GeneratedValue
    private int id;

    @NotBlank
    private String name;

    public int getId(){
        return id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }


}
