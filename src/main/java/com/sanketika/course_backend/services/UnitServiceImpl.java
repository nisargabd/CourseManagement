package com.sanketika.course_backend.services;

import com.sanketika.course_backend.dto.UnitDto;
import com.sanketika.course_backend.entity.Course;
import com.sanketika.course_backend.entity.Unit;
import com.sanketika.course_backend.exceptions.ResourceNotFoundException;
import com.sanketika.course_backend.mapper.UnitMapper;
import com.sanketika.course_backend.repositories.CourseRepository;
import com.sanketika.course_backend.repositories.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UnitServiceImpl implements UnitService {

    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UnitMapper unitMapper;

    @Override
    public List<UnitDto> getUnitsByCourse(UUID courseId) {
        return unitRepository.findByCourseId(courseId).stream()
                .map(unitMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UnitDto getUnitById(UUID id) {
        try {
            Unit unit = unitRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Unit not found"));
            return unitMapper.toDto(unit);
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    @Override
    public UnitDto updateUnit(UUID id, UnitDto dto) {
        try{
        Unit existing = unitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Unit not found"));

        existing.setTitle(dto.getTitle());
        existing.setContent(dto.getContent());

        if (dto.getCourseId() != null) {
            Course course = courseRepository.findById(dto.getCourseId())
                    .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
            existing.setCourse(course);
        }

        Unit updated = unitRepository.save(existing);
        return unitMapper.toDto(updated);
    }catch(Exception e){
            System.out.println(e);
        }
        return null;
    }

    @Override
    public UnitDto createUnit(UnitDto dto){
        Unit unit = unitMapper.toEntity(dto);
        Unit saved = unitRepository.save(unit);
        return unitMapper.toDto(saved);
    }

    @Override
    public void deleteUnit(UUID id) {
        try{
        Unit unit = unitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Unit not found"));
        unitRepository.delete(unit);
    }catch (Exception e){
            System.out.println(e);
        }
    }
}
