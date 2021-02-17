package com.mediscreen.patients.service;

import com.mediscreen.patients.model.AddressModel;
import com.mediscreen.patients.model.PatientModel;
import com.mediscreen.patients.repository.PatientRepository;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    private final Logger logger = LoggerFactory.getLogger(PatientService.class);

    private final PatientRepository patientRep;
    private final AddressService addressService;

    public PatientService(PatientRepository patientRep, AddressService addressService) {
        this.patientRep = patientRep;
        this.addressService = addressService;
    }

    /**
     * Get a list of all patients
     *
     * @return list of PatientModel containing all patient models
     */
    public List<PatientModel> getAllPatients() {
        return patientRep.findAll();
    }

    public List<PatientModel> getAllPatientsByAddress(String street, String city,
                                                      String country) {
        return patientRep.findAllByAddressStreetAndAddressCityAndAddressCountry(street, city, country);
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
     * Check if the combination of given and family name and birthdate already exists
     * @param givenName the given name of the patient
     * @param familyName the family name of the patient
     * @param birthdate the birthdate of the patient
     * @return true if the combination already exists, false if the combination doesn't exist
     */
    public boolean checkGivenAndFamilyNamesAndBirthDateExist(String givenName, String familyName, LocalDate birthdate) {
        return patientRep.existsByGivenNameAndFamilyNameAndBirthdate(givenName, familyName, birthdate);
    }

    /**
     * Check if a patient Id already exists
     * @param id the patient ID
     * @return true if patient ID already exists, false if patient ID doesn't exist
     */
    public boolean checkIdExists(int id) {
        return patientRep.existsById(id);
    }

    /**
     * Set a new AddressModel from data inside the request, to the PatientModel
     * Save a new patient in the DB after checking if the given, family names and birthdate exist already
     *
     * @param patient the PatientModel to save
     * @return boolean, true if the patient was saved, false if not saved
     */
    public void savePatient(PatientModel patient) {
        AddressModel address;
        address = new AddressModel();
        address.setStreet(patient.getAddress().getStreet());
        address.setCity(patient.getAddress().getCity());
        address.setPostcode(patient.getAddress().getPostcode());
        address.setDistrict(patient.getAddress().getDistrict());
        address.setState(patient.getAddress().getState());
        address.setCountry(patient.getAddress().getCountry());
        patient.setAddress(address);
        patientRep.save(patient);
    }

    /**
     * Set a new AddressModel from data inside the request, to the PatientModel
     * Save a new patient in the DB
     *
     * @param patient the PatientModel to save
     */
    public void updatePatient(PatientModel patient) {
        AddressModel address = addressService.getAddressById(patient.getId());

        address.setStreet(patient.getAddress().getStreet());
        address.setCity(patient.getAddress().getCity());
        address.setPostcode(patient.getAddress().getPostcode());
        address.setDistrict(patient.getAddress().getDistrict());
        address.setState(patient.getAddress().getState());
        address.setCountry(patient.getAddress().getCountry());

        patient.setAddress(address);

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