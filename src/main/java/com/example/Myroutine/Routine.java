package com.example.Myroutine;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Routine {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private boolean done;
    private LocalDate date;

    @ManyToOne
    private User user;

    public Routine(Long id,
                   String title,
                   String description,
                   LocalDate date,
                   User user) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.user = user;
        this.done = false;
    }

    protected Routine() {}

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isDone() {
        return done;
    }

    public void toggleDone() {
        this.done = !this.done;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description){
        this.description = description;
    }
    public LocalDate getDate(){
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
