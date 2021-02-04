package com.mediscreen.patients.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mediscreen.patients.model.PatientModel;

public interface PatientRepository extends JpaRepository<PatientModel, Integer> {

}
