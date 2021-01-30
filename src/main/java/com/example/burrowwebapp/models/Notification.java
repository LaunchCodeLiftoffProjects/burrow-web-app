package com.example.burrowwebapp.models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.time.LocalDate;

@Entity
public class Notification extends AbstractEntity
{

    @ManyToOne
    private User user;

    @OneToOne
    private Component component;

    private boolean isActive;

    private LocalDate replacedDate;

    private Long daysBetweenReplacements;

    public Notification(){
        this.isActive = false;
    }

    public Notification(String message, User user, LocalDate installDate, Long daysBetweenReplacements){

        this.setName(message);
        this.user = user;
        this.replacedDate = installDate;
        this.daysBetweenReplacements = daysBetweenReplacements;
        this.isActive = false;
    }

    public Notification(String s, LocalDate installDate, Long daysBetweenReplacements) {

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
        this.setName("You last replaced the " + this.getComponent().getName() + " for " + this.getComponent().getDevice().getName() + " on " + this.getReplacedDate() + ". It should be replaced every " + this.getDaysBetweenReplacements() + " days.");
    }
}
