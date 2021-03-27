package com.mediscreen.patients.service;

import com.mediscreen.patients.model.AddressModel;
import com.mediscreen.patients.model.PatientModel;
import com.mediscreen.patients.model.PatientModelDTO;
import com.mediscreen.patients.repository.PatientRepository;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    /**
     * Get a list of all patients By Address (combination of street + city + country)
     *
     * @return list of PatientModel containing all patient models with that address
     */
    public List<PatientModel> getAllPatientsByAddress(String street, String city,
                                                      String country) {
        return patientRep.findAllByAddressStreetAndAddressCityAndAddressCountry(street, city, country);
    }

    /**
     * Get a patient model by ID
     * @param id int of the patient ID
     * @return PatientModel found with the patient ID
     */
    public PatientModel getPatientById(int id) {
        return patientRep.findById(id);
    }

    /**
     * Check if the combination of given and family name and birthdate already exists
     * @param givenName string of the given name of the patient
     * @param familyName string of the family name of the patient
     * @param birthdate string of the birthdate of the patient
     * @return true if the combination already exists, false if the combination doesn't exist
     */
    public boolean checkGivenAndFamilyNamesAndBirthDateExist(String givenName, String familyName, LocalDate birthdate) {
        return patientRep.existsByGivenNameAndFamilyNameAndBirthdate(givenName, familyName, birthdate);
    }

    /**
     * Check if a patient Id already exists
     * @param id int of the patient ID
     * @return true if patient ID already exists, false if patient ID doesn't exist
     */
    public boolean checkIdExists(int id) {
        return patientRep.existsById(id);
    }

    /**
     * Convert a PatientModel into a PatientModelDTO
     *
     * @param patientModel
     * @return a PatientModelDTO
     */
    public PatientModelDTO patientModelDTO(PatientModel patientModel) {
        PatientModelDTO patientModelDTO = new PatientModelDTO();

        patientModelDTO.setId(patientModel.getId());
        patientModelDTO.setGivenName(patientModel.getGivenName());
        patientModelDTO.setFamilyName(patientModel.getFamilyName());
        patientModelDTO.setAddress(patientModel.getAddress());
        patientModelDTO.setBirthdate(patientModel.getBirthdate().toString());
        patientModelDTO.setGender(patientModel.getGender());
        patientModelDTO.setEmailAddress(patientModel.getEmailAddress());
        patientModelDTO.setPhoneNumber(patientModel.getPhoneNumber());

        return patientModelDTO;
    }

    /**
     * Convert a PatientModel list into a PatientModelDTO list
     *
     * @param patientModelList a list of patients in PatientModel
     * @return a list of PatientModelDTO
     */
    public List<PatientModelDTO> patientModelToDtoList(List<PatientModel> patientModelList) {
        List<PatientModelDTO> patientModelDTOList = new ArrayList<>();
        for (PatientModel patientModel: patientModelList) {
            PatientModelDTO patientModelDTO = new PatientModelDTO();
            patientModelDTO.setId(patientModel.getId());
            patientModelDTO.setGivenName(patientModel.getGivenName());
            patientModelDTO.setFamilyName(patientModel.getFamilyName());
            patientModelDTO.setAddress(patientModel.getAddress());
            patientModelDTO.setBirthdate(patientModel.getBirthdate().toString());
            patientModelDTO.setGender(patientModel.getGender());
            patientModelDTO.setEmailAddress(patientModel.getEmailAddress());
            patientModelDTO.setPhoneNumber(patientModel.getPhoneNumber());
            patientModelDTOList.add(patientModelDTO);
        }
        return patientModelDTOList;
    }

    /**
     * Set a new AddressModel from data inside the request, to the PatientModel
     * Save a new patient in the DB after checking if the given, family names and birthdate exist already
     *
     * @param patient the PatientModel to save
     * @return patient saved
     */
    public PatientModel savePatient(PatientModel patient) {
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

        return patient;
    }

    /**
     * Set a new AddressModel from data inside the request, to the PatientModel
     * Save a new patient in the DB
     *
     * @param patient the PatientModel to save
     * @return patient updated
     */
    public PatientModel updatePatient(PatientModel patient) {
        AddressModel address = addressService.getAddressById(patient.getId());

        address.setStreet(patient.getAddress().getStreet());
        address.setCity(patient.getAddress().getCity());
        address.setPostcode(patient.getAddress().getPostcode());
        address.setDistrict(patient.getAddress().getDistrict());
        address.setState(patient.getAddress().getState());
        address.setCountry(patient.getAddress().getCountry());

        patient.setAddress(address);

        patientRep.save(patient);
        return patient;
    }

    /**
     * Delete an existent patient from the DB
     * @param id the integer of patient ID
     */
    public void deletePatientById(int id) {
        patientRep.deleteById(id);
    }
}