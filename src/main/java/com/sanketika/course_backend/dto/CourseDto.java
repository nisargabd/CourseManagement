package com.sanketika.course_backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class CourseDto {
    private UUID id;

    @NotBlank(message = "name is required")
    private String name;

    private String description;
    private String board;
    private String medium;
    private String grade;
    private String subject;
    private List<UnitDto> units;

    public CourseDto() {}
}
