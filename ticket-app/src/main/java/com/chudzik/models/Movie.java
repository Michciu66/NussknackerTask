package com.chudzik.models;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MOVIE_ID")
    private Long id;
    @Column(name = "MOVIE_TITLE", nullable = false, length = 128)
    private String title;
    @Column(name = "MOVIE_DUR", nullable = false)
    private int durationInMinutes;
    @Column(name = "MOVIE_DESC", nullable = false, length = 1024)
    private String description;

    public Movie(){

    }

    public Movie(String title, int duration, String description) {
        this.title = title;
        this.durationInMinutes = duration;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDuration() {
        return durationInMinutes;
    }

    public void setDuration(int duration) {
        this.durationInMinutes = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Movie)) {
            return false;
        }
        Movie other = (Movie) o;
        return this.id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.title, this.durationInMinutes, this.description);
    }

    @Override
    public String toString() {
        return "Movie [id=" + id + ", title=" + title + ", duration=" + durationInMinutes + ", description=" + description + "]";
    }

}
