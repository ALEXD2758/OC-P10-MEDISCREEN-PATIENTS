package com.mediscreen.patients.service;

import com.mediscreen.patients.model.PatientModel;
import com.mediscreen.patients.repository.PatientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    private Logger logger = LoggerFactory.getLogger(PatientService.class);

    private PatientRepository patientRep;

    public PatientService(PatientRepository patientRep) {
        this.patientRep = patientRep;
    }

    /**
     * Get a list of all patients
     *
     * @return list of PatientModel containing all patient models
     */
    public List<PatientModel> getAllPatients() {
        return patientRep.findAll();
    }

    /**
     * Check if the combination of given and family name already exists
     * @param givenName the given name of the patient
     * @param familyName the family name of the patient
     * @return true if the combination already exists
     * @return false if the combination doesn't exist
     */
    public boolean checkGivenAndFamilyNamesExist(String givenName, String familyName) {
        return patientRep.existsByGivenNameAndFamilyName(givenName, familyName);
    }

    /**
     * Check if a patient Id already exists
     * @param id the patient ID
     * @return true if patient ID already exists
     * @return false if patient ID doesn't exist
     */
    public boolean checkIdExists(int id) {
        return patientRep.existsById(id);
    }

    /**
     * Get a patient model by ID
     * @param id the patient ID
     * @return UserModel found with the patient ID
     */
    public PatientModel getPatientById(int id) {
        return patientRep.findById(id);
    }

    /**
     * Get a list of patients by given name
     * @param givenName the string of the given name
     * @return list of PatientModel found with the given name
     */
    public List<PatientModel> getPatientByGivenName(String givenName) {
        return patientRep.findByGivenName(givenName);
    }

    /**
     * Get a list of patients by family name
     * @param familyName the string of the family name
     * @return list of PatientModel found with the family name
     */
    public List<PatientModel> getPatientByFamilyName(String familyName) {
        return patientRep.findByFamilyName(familyName);
    }

    /**
     * Save a new patient in the DB
     * @param patient the PatientModel to save
     */
    public void savePatient(PatientModel patient) {
        patientRep.save(patient);
    }

    /**
     * Delete an existent patient from the DB
     * @param id the integer of patient ID
     */
    public void deletePatientById(int id) {
        patientRep.deleteById(id);
    }
}