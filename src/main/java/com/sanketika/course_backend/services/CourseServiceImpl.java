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

    @Override
    @Cacheable(value = "courses", key = "#id")
    public CourseDto getCourseById(UUID id) {
        try {
            Course course = courseRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Course not found with ID: " + id));
            logger.info("Fetching course from DB with ID {}", id);
            return courseMapper.toDto(course);
        } catch (ResourceNotFoundException ex) {
            logger.error("Course not found with ID {}: {}", id, ex.getMessage());
            throw ex; // rethrow to be handled by GlobalExceptionHandler
        } catch (Exception ex) {
            logger.error("Unexpected error while fetching course with ID {}", id, ex);
            throw ex;
        }
    }


    @Override
    public CourseDto createCourse(CourseDto dto) {
        Course course = courseMapper.toEntity(dto);
        Course saved = courseRepository.save(course);
        return courseMapper.toDto(saved);
    }

    @Override
    @CachePut(value = "courses", key = "#id")
    public CourseDto updateCourse(UUID id, CourseDto dto) {
        try {
            Course existing = courseRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Course not found with ID: " + id));

            existing.setName(dto.getName());
            existing.setDescription(dto.getDescription());
            existing.setBoard(dto.getBoard());
            existing.setMedium(dto.getMedium());
            existing.setGrade(dto.getGrade());
            existing.setSubject(dto.getSubject());

            Course updated = courseRepository.save(existing);
            logger.info("Updated course successfully with ID {}", id);
            return courseMapper.toDto(updated);

        } catch (ResourceNotFoundException ex) {
            logger.error("Cannot update course, not found ID {}: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            logger.error("Unexpected error while updating course with ID {}", id, ex);
            throw ex;
        }
    }

    @Override
    @CacheEvict(value = "courses", key = "#courseId")
    public void deleteCourse(UUID courseId) {
        try {
            logger.warn("Deleting course with ID: {}", courseId);

            Course course = courseRepository.findById(courseId)
                    .orElseThrow(() -> new ResourceNotFoundException("Course not found with ID: " + courseId));

            List<Unit> units = unitRepository.findByCourseId(courseId);
            if (!units.isEmpty()) {
                for (Unit u : units) {
                    u.setCourse(null);
                }
                unitRepository.saveAllAndFlush(units);
            }
            course.setUnits(null);
            courseRepository.delete(course);

            logger.info("Course deleted successfully with ID {}", courseId);

        } catch (ResourceNotFoundException ex) {
            logger.error("Cannot delete course, not found ID {}: {}", courseId, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            logger.error("Unexpected error while deleting course with ID {}", courseId, ex);
            throw ex;
        }
    }


}
