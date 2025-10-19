package com.sanketika.course_backend.mapper;

import com.sanketika.course_backend.dto.UnitDto;
import com.sanketika.course_backend.entity.Course;
import com.sanketika.course_backend.entity.Unit;
import org.springframework.stereotype.Component;

@Component
public class UnitMapper {

    public UnitDto toDto(Unit unit) {
        if (unit == null) return null;
        UnitDto dto = new UnitDto();
        dto.setId(unit.getId());
        dto.setTitle(unit.getTitle());
        dto.setContent(unit.getContent());
        dto.setCourseId(unit.getCourse() != null ? unit.getCourse().getId() : null);
        return dto;
    }

    public Unit toEntity(UnitDto dto, Course course) {
        if (dto == null) return null;
        Unit unit = new Unit();
        unit.setId(dto.getId());
        unit.setTitle(dto.getTitle());
        unit.setContent(dto.getContent());
        unit.setCourse(course); // ✅ ensures courseId is set
        return unit;
    }
}
