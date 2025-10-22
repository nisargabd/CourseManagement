package com.sanketika.course_backend.services;

import com.sanketika.course_backend.dto.FilterOptionsDto;
import com.sanketika.course_backend.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FilterService {
    @Autowired
    private CourseRepository courseRepository;

    public FilterOptionsDto getFilterOptions() {
        FilterOptionsDto dto = new FilterOptionsDto();
        dto.setBoards(courseRepository.findDistinctBoards());
        dto.setMediums(courseRepository.findDistinctMediums());
        dto.setGrades(courseRepository.findDistinctGrades());
        dto.setSubjects(courseRepository.findDistinctSubjects());
        return dto;
    }
}
