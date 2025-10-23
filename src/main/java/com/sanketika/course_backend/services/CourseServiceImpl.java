package com.sanketika.course_backend.services;

import com.sanketika.course_backend.dto.CourseDto;
import com.sanketika.course_backend.entity.Course;
import com.sanketika.course_backend.excepections.ResourceNotFoundException;
import com.sanketika.course_backend.mapper.CourseMapper;
import com.sanketika.course_backend.repositories.CourseRepository;
import com.sanketika.course_backend.specification.CourseSpecification;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseMapper courseMapper;

    @Override
    public List<CourseDto> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(courseMapper::toDto)
                .collect(Collectors.toList());
    }

//    @Override
//    public List<Course> findAllFiltered(String q, String board, String medium, String grade, String subject) {
//        Specification<Course> spec = CourseSpecification.filterBy(q, board, medium, grade, subject);
//        return courseRepository.findAll(spec);
//    }
    @Override
    public CourseDto getCourseById(UUID id) {
        try {
            Course course = courseRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
            return courseMapper.toDto(course);
        }catch(Exception e){
            System.out.println(e);
        }
        return null;
    }

    @Override
    public CourseDto createCourse(CourseDto dto) {
        Course course = courseMapper.toEntity(dto);
        Course saved = courseRepository.save(course);
        return courseMapper.toDto(saved);
    }

    @Override
    public CourseDto updateCourse(UUID id, CourseDto dto) {
        try{
        Course existing = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        existing.setName(dto.getName());
        existing.setDescription(dto.getDescription());
        existing.setBoard(dto.getBoard());
        existing.setMedium(dto.getMedium());
        existing.setGrade(dto.getGrade());
        existing.setSubject(dto.getSubject());

        Course updated = courseRepository.save(existing);
        return courseMapper.toDto(updated);
    }catch(Exception e){
            System.out.println(e);
        }
        return null;
    }

    @Override
    public void deleteCourse(UUID id) {
        try{
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
        courseRepository.delete(course);
    }catch(Exception e){
            System.out.println(e);
        }
    }
}
