package com.example.hms.controller;

import com.example.hms.model.Staff;
import com.example.hms.repository.StaffRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/staff")
public class StaffController {

    private final StaffRepository staffRepository;

    public StaffController(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    @GetMapping
    public List<Staff> getAll() {
        return staffRepository.findAll();
    }

    @PostMapping
    public Staff add(@RequestBody Staff staff) {
        return staffRepository.save(staff);
    }

    @GetMapping("/{id}")
    public Staff getById(@PathVariable Long id) {
        return staffRepository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public Staff update(@PathVariable Long id, @RequestBody Staff updated) {
        updated.setId(id);
        return staffRepository.save(updated);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        staffRepository.deleteById(id);
    }
}