package com.example.burrowwebapp.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.time.LocalDate;

@Entity
public class Notification extends AbstractEntity
{

    @OneToOne(mappedBy = "notification")
    private Component component;

    private boolean isActive;

    private LocalDate replacedDate;

    private long daysBetweenReplacements;

    public Notification(){
        this.isActive = false;
    }

    public Notification(String message, LocalDate installDate, long daysBetweenReplacements){

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

    public long getDaysBetweenReplacements()
    {
        return daysBetweenReplacements;
    }

    public void setDaysBetweenReplacements(long daysBetweenReplacements)
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
}
