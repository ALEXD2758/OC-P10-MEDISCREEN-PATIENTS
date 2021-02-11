package com.mediscreen.patients.repository;

import org.joda.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import com.mediscreen.patients.model.PatientModel;

import java.util.List;

public interface PatientRepository extends JpaRepository<PatientModel, Integer> {

    List<PatientModel> findByFamilyName(String familyName);
    List<PatientModel> findByGivenName(String givenName);

    List<PatientModel> findAll();
    List<PatientModel> findAllByAddressStreet(String street);

    PatientModel findById(int id);

    boolean existsById(int id);

    boolean existsByGivenNameAndFamilyNameAndBirthdate(String givenName, String familyName, LocalDate birthdate);
}