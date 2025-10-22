package com.sanketika.course_backend.controllers;

import com.sanketika.course_backend.dto.FilterOptionsDto;
import com.sanketika.course_backend.services.FilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/courses/filters")
//@CrossOrigin(origins = "*")
public class FilterController {

    @Autowired
    private FilterService filterService;

    @GetMapping
    public ResponseEntity<FilterOptionsDto> getFilterOptions() {
        return ResponseEntity.ok(filterService.getFilterOptions());
    }

}
