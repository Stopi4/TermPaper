package com.University.TempPaper.Model;

import java.util.LinkedList;
import java.util.List;

public class Composition {
    private int id;
    private String name;
    private double duration;
    private List<String> genres;
    private String assemblageName;
    private String performer;

    public Composition() {}

    public Composition(int id, String name, double duration, LinkedList<String> genres, String assemblageName, String performer) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.genres = genres;
        this.assemblageName = assemblageName;
        this.performer = performer;
    }

    @Override
    public String toString() {
        return "Composition{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", duration=" + duration +
                ", performer=" + performer +
                ", assemblageName='" + assemblageName + '\'' + '\n' + '\'' +
                "genres=" + genres.toString() + '\'' +
                '}';
    }

    public String getAssemblageName() {
        return assemblageName;
    }

    public void setAssemblageName(String assemblageName) {
        this.assemblageName = assemblageName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getDuration() {
        return duration;
    }
    public void setDuration(double duration) {
        this.duration = duration;
    }
    public String getPerformer() {
        return performer;
    }
    public void setPerformer(String performer) {
        this.performer = performer;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }
}
