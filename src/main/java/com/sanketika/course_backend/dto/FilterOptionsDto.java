package com.sanketika.course_backend.dto;

import lombok.Data;

import java.util.List;

@Data
public class FilterOptionsDto {
    private List<String> boards;
    private List<String> mediums;
    private List<String> grades;
    private List<String> subjects;
}
