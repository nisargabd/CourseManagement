package com.sanketika.course_backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "units")
public class Unit {
    @Id
    @GeneratedValue
    private UUID id;
    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    @JsonBackReference
    private Course course;

    public Unit(){

    }
    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }
}
