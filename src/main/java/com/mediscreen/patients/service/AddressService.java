package com.mediscreen.patients.service;

import com.mediscreen.patients.model.AddressModel;
import com.mediscreen.patients.repository.AddressRepository;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    private final Logger logger = LoggerFactory.getLogger(AddressService.class);

    private final AddressRepository addressRep;

    public AddressService(AddressRepository addressRep) {
        this.addressRep = addressRep;
    }

    /**
     * Get a patient model by ID
     * @param id the patient ID
     * @return UserModel found with the patient ID
     */
    public AddressModel getAddressById(int id) {
        return addressRep.findById(id);
    }

    /**
     * Get a patient model by a combination of address fields (see parameters)
     * @param street
     * @param city
     * @param postcode
     * @param country
     * @return an address model that already exists
     */
    public List<AddressModel> getAllPatientsWithExistentAddress (String street, String city, String postcode,
                                                           String country) {
        return addressRep.findAllByStreetAndCityAndPostcodeAndCountry(street, city, postcode, country);
    }

    /**
     * Check if the combination of the address already exists
     * @param street
     * @param city
     * @param postcode
     * @param country
     * @return true if the combination already exists, false if the combination doesn't exist
     */
    /*
    public boolean checkAddressCombinationExist(String street, String city, String postcode, String country) {
        return addressRep.existsByStreetAndCityAndPostcodeAndCountry(street, city, postcode, country);
    }
    */
}