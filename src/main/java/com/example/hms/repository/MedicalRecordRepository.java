package com.example.hms.repository;
import com.example.hms.model.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {}