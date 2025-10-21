package com.sanketika.course_backend.specification;

import com.sanketika.course_backend.entity.Course;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class CourseSpecification {

    public static Specification<Course> filterBy(final String q, final String board, final String medium, final String grade, final String subject) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (q != null && !q.isBlank()) {
                String like = "%" + q.trim().toLowerCase() + "%";
                // search in name and description
                predicates.add(cb.or(
                        cb.like(cb.lower(root.get("name")), like),
                        cb.like(cb.lower(root.get("description")), like)
                ));
            }

            if (board != null && !board.isBlank()) {
                predicates.add(cb.equal(cb.lower(root.get("board")), board.trim().toLowerCase()));
            }
            if (medium != null && !medium.isBlank()) {
                predicates.add(cb.equal(cb.lower(root.get("medium")), medium.trim().toLowerCase()));
            }
            if (grade != null && !grade.isBlank()) {
                predicates.add(cb.equal(cb.lower(root.get("grade")), grade.trim().toLowerCase()));
            }
            if (subject != null && !subject.isBlank()) {
                predicates.add(cb.equal(cb.lower(root.get("subject")), subject.trim().toLowerCase()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
