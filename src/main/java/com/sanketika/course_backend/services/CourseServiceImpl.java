package com.sanketika.course_backend.services;

import com.sanketika.course_backend.dto.CourseDto;
import com.sanketika.course_backend.entity.Course;
import com.sanketika.course_backend.entity.Unit;
import com.sanketika.course_backend.exceptions.ResourceNotFoundException;
import com.sanketika.course_backend.mapper.CourseMapper;
import com.sanketika.course_backend.repositories.CourseRepository;
import com.sanketika.course_backend.repositories.UnitRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {


    private static final Logger logger = LoggerFactory.getLogger(CourseService.class);


    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private CourseMapper courseMapper;

    @Override
    public Page<CourseDto> getAllCourses(Pageable p) {
        Page<Course> page = courseRepository.findAll(p);
        return page.map(courseMapper::toDto);
    }

    @Cacheable(value = "courses",key = "#id")
    @Override
    public CourseDto getCourseById(UUID id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
        logger.info("Fetching course from DB with id {}", id);
        return courseMapper.toDto(course);
    }

    @Override
    public CourseDto createCourse(CourseDto dto) {
        Course course = courseMapper.toEntity(dto);
        Course saved = courseRepository.save(course);
        return courseMapper.toDto(saved);
    }

    @CachePut(value = "courses",key = "#id")
    @Override
    public CourseDto updateCourse(UUID id, CourseDto dto) {
        Course existing = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        existing.setName(dto.getName());
        existing.setDescription(dto.getDescription());
        existing.setBoard(dto.getBoard());
        existing.setMedium(dto.getMedium());
        existing.setGrade(dto.getGrade());
        existing.setSubject(dto.getSubject());

        Course updated = courseRepository.save(existing);
        logger.info("Updating course with id {}",id);
        return courseMapper.toDto(updated);
    }

    @Override
    @Transactional
    @CacheEvict(value = "courses", key = "#courseId")
    public void deleteCourse(UUID courseId) {
        logger.info("Deleting course with id {}", courseId);

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        // Explicitly detach units BEFORE deleting the course
        List<Unit> units = unitRepository.findByCourseId(courseId);
        if (!units.isEmpty()) {
            for (Unit u : units) {
                u.setCourse(null);
            }
            unitRepository.saveAllAndFlush(units);  // ensures FK null before delete
        }

        // Detach from JPA context to avoid cascade reattach issues
        course.setUnits(null);

        courseRepository.delete(course);
        logger.info("Course deleted successfully with id {}", courseId);
    }

}
