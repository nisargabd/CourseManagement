package com.sanketika.course_backend.controllers;

import com.sanketika.course_backend.dto.UnitDto;
import com.sanketika.course_backend.mapper.ResponseMapper;
import com.sanketika.course_backend.services.UnitService;
import com.sanketika.course_backend.utils.ApiEnvelope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/units")
public class UnitController {

    @Autowired
    private UnitService unitService;

    // ✅ Get all units of a specific course
    @GetMapping("/course/{courseId}")
    public ResponseEntity<ApiEnvelope<List<UnitDto>>> getUnitsByCourse(@PathVariable UUID courseId) {
        List<UnitDto> units = unitService.getUnitsByCourse(courseId);
        ApiEnvelope<List<UnitDto>> response = ResponseMapper.success(
                "api.unit.list",
                "Units fetched successfully",
                units
        );
        return ResponseEntity.ok(response);
    }

    // ✅ Get single unit by ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiEnvelope<UnitDto>> getUnitById(@PathVariable UUID id) {
        UnitDto unit = unitService.getUnitById(id);
        ApiEnvelope<UnitDto> response = ResponseMapper.success(
                "api.unit.get",
                "Unit fetched successfully",
                unit
        );
        return ResponseEntity.ok(response);
    }

    // ✅ Update a unit
    @PutMapping("/{id}")
    public ResponseEntity<ApiEnvelope<UnitDto>> updateUnit(@PathVariable UUID id, @RequestBody UnitDto dto) {
        UnitDto updated = unitService.updateUnit(id, dto);
        ApiEnvelope<UnitDto> response = ResponseMapper.success(
                "api.unit.update",
                "Unit updated successfully",
                updated
        );
        return ResponseEntity.ok(response);
    }

    // ✅ Delete a unit
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiEnvelope<Void>> deleteUnit(@PathVariable UUID id) {
        unitService.deleteUnit(id);
        ApiEnvelope<Void> response = ResponseMapper.success(
                "api.unit.delete",
                "Unit deleted successfully",
                null
        );
        return ResponseEntity.ok(response);
    }
}
