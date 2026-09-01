package com.example.hms.controller;

import com.example.hms.model.LabTest;
import com.example.hms.repository.LabTestRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lab-tests")
public class LabTestController {

    private final LabTestRepository labTestRepository;

    public LabTestController(LabTestRepository labTestRepository) {
        this.labTestRepository = labTestRepository;
    }

    @GetMapping
    public List<LabTest> getAll() {
        return labTestRepository.findAll();
    }

    @PostMapping
    public LabTest add(@RequestBody LabTest labTest) {
        return labTestRepository.save(labTest);
    }

    @GetMapping("/{id}")
    public LabTest getById(@PathVariable Long id) {
        return labTestRepository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public LabTest update(@PathVariable Long id, @RequestBody LabTest updated) {
        updated.setId(id);
        return labTestRepository.save(updated);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        labTestRepository.deleteById(id);
    }
}