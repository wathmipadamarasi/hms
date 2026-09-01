package com.example.hms.controller;

import com.example.hms.model.MedicalRecord;
import com.example.hms.repository.MedicalRecordRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medical-records")
public class MedicalRecordController {

    private final MedicalRecordRepository medicalRecordRepository;

    public MedicalRecordController(MedicalRecordRepository medicalRecordRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
    }

    @GetMapping
    public List<MedicalRecord> getAll() {
        return medicalRecordRepository.findAll();
    }

    @PostMapping
    public MedicalRecord add(@RequestBody MedicalRecord record) {
        return medicalRecordRepository.save(record);
    }

    @GetMapping("/{id}")
    public MedicalRecord getById(@PathVariable Long id) {
        return medicalRecordRepository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public MedicalRecord update(@PathVariable Long id, @RequestBody MedicalRecord updated) {
        updated.setId(id);
        return medicalRecordRepository.save(updated);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        medicalRecordRepository.deleteById(id);
    }
}