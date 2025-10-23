package com.sanketika.course_backend.repositories;

import com.sanketika.course_backend.dto.CourseDto;
import com.sanketika.course_backend.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CourseRepository extends JpaRepository<Course, UUID>, JpaSpecificationExecutor<Course> {

    Page<Course> findAll(Pageable pageable);

    @Query("SELECT DISTINCT c.board FROM Course c WHERE c.board IS NOT NULL")
    List<String> findDistinctBoards();

    @Query("SELECT DISTINCT c.medium FROM Course c WHERE c.medium IS NOT NULL")
    List<String> findDistinctMediums();

    @Query("SELECT DISTINCT c.grade FROM Course c WHERE c.grade IS NOT NULL")
    List<String> findDistinctGrades();

    @Query("SELECT DISTINCT c.subject FROM Course c WHERE c.subject IS NOT NULL")
    List<String> findDistinctSubjects();
}
