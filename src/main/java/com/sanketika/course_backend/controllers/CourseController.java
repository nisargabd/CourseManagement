package com.sanketika.course_backend.controllers;

import com.sanketika.course_backend.dto.CourseDto;
import com.sanketika.course_backend.mapper.ResponseMapper;
import com.sanketika.course_backend.services.CourseService;
import com.sanketika.course_backend.utils.ApiEnvelope;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    // ✅ Get all courses
    @GetMapping("/get")
    public ResponseEntity<Page<CourseDto>> getAllCourses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(courseService.getAllCourses(pageable));

    }
//    @GetMapping
//    public List<Course> list(
//            @RequestParam(required = false) String q,
//            @RequestParam(required = false) String board,
//            @RequestParam(required = false) String medium,
//            @RequestParam(required = false) String grade,
//            @RequestParam(required = false) String subject
//    ) {
//        return courseService.findAllFiltered(q, board, medium, grade, subject);
//    }

    // ✅ Get course by ID
    @GetMapping("/get/{id}")
    public ResponseEntity<ApiEnvelope<CourseDto>> getCourseById(@PathVariable UUID id) {
        CourseDto course = courseService.getCourseById(id);
        ApiEnvelope<CourseDto> response = ResponseMapper.success(
                "api.course.get",
                "Course fetched successfully",
                course
        );
        return ResponseEntity.ok(response);
    }

    // ✅ Create course
    @PostMapping("/add")
    public ResponseEntity<ApiEnvelope<CourseDto>> createCourse(@Valid @RequestBody CourseDto dto) {
        CourseDto created = courseService.createCourse(dto);
        ApiEnvelope<CourseDto> response = ResponseMapper.success(
                "api.course.create",
                "Course created successfully",
                created
        );
        return ResponseEntity.ok(response);
    }

    // ✅ Update course
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiEnvelope<CourseDto>> updateCourse(@PathVariable UUID id, @Valid @RequestBody CourseDto dto) {
        CourseDto updated = courseService.updateCourse(id, dto);
        ApiEnvelope<CourseDto> response = ResponseMapper.success(
                "api.course.update",
                "Course updated successfully",
                updated
        );
        return ResponseEntity.ok(response);
    }

    // ✅ Delete course
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiEnvelope<Void>> deleteCourse(@PathVariable UUID id) {
        courseService.deleteCourse(id);
        ApiEnvelope<Void> response = ResponseMapper.success(
                "api.course.delete",
                "Course deleted successfully",
                null
        );
        return ResponseEntity.ok(response);
    }
}
