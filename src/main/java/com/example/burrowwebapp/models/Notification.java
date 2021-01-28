package com.example.burrowwebapp.models;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.time.LocalDate;

@Entity
public class Notification extends AbstractEntity
{

    @OneToOne
    private Component component;

    private boolean isActive;

    private LocalDate replacedDate;

    private Long daysBetweenReplacements;

    public Notification(){
        this.isActive = false;
    }

    public Notification(String message, LocalDate installDate, Long daysBetweenReplacements){

        this.setName(message);
        this.replacedDate = installDate;
        this.daysBetweenReplacements = daysBetweenReplacements;
        this.isActive = false;
    }

    public Component getComponent()
    {
        return component;
    }

    public void setComponent(Component component)
    {
        this.component = component;
    }

    public boolean isActive()
    {
        return isActive;
    }

    public void setActive(boolean active)
    {
        isActive = active;
    }

    public LocalDate getReplacedDate()
    {
        return replacedDate;
    }

    public void setReplacedDate(LocalDate replacedDate)
    {
        this.replacedDate = replacedDate;
    }

    public Long getDaysBetweenReplacements()
    {
        return daysBetweenReplacements;
    }

    public void setDaysBetweenReplacements(Long daysBetweenReplacements)
    {
        this.daysBetweenReplacements = daysBetweenReplacements;
    }

    public void needsToBeReplaced(){
        LocalDate nextReplacementDate = this.replacedDate.plusDays(daysBetweenReplacements);
        LocalDate today = LocalDate.now();
        if(today.isAfter(this.replacedDate.plusDays(daysBetweenReplacements))){
            this.setActive(true);
        }else{
            this.setActive(false);
        }
    }

    public void replaceComponent(){
        this.setReplacedDate(LocalDate.now());
        this.setActive(false);
    }

    public void setMessage(){
        this.setName("You last replaced " + this.getComponent().getName() + this.getComponent().getDevice().getName() + " on " + this.getReplacedDate() + ". It should be replaced every " + this.getDaysBetweenReplacements() + " days.");
    }
}
