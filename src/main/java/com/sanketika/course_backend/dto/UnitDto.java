package com.sanketika.course_backend.dto;

import com.sanketika.course_backend.entity.Course;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.UUID;

@Data
public class UnitDto {
    private UUID id;
    @NotBlank(message = "Title is required")
    private String title;
    private String content;
    private UUID courseId;

    public UnitDto(){

    }
}
