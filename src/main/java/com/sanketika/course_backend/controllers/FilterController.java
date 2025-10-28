package com.sanketika.course_backend.controllers;

import com.sanketika.course_backend.dto.FilterOptionsDto;
import com.sanketika.course_backend.services.FilterOptionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for filter options
 */
@RestController
@CrossOrigin(origins = {"http://localhost:4800", "http://localhost:4200"})
@RequestMapping("/api/filters")
public class FilterController {

    @Autowired
    private FilterOptionsService filterOptionsService;

    /**
     * Get all available filter options
     * @return FilterOptionsDto containing boards, mediums, grades, and subjects
     */
    @GetMapping("/options")
    public ResponseEntity<FilterOptionsDto> getFilterOptions() {
        FilterOptionsDto options = filterOptionsService.getFilterOptions();
        return ResponseEntity.ok(options);
    }
}