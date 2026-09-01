package com.example.hms.repository;
import com.example.hms.model.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
public interface MedicineRepository extends JpaRepository<Medicine, Long> {}