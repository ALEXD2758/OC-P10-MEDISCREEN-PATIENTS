package com.mediscreen.patients.repository;

import com.mediscreen.patients.model.AddressModel;
import org.joda.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<AddressModel, Integer> {

    AddressModel findById(int id);

    List<AddressModel> findAllByStreetAndCityAndPostcodeAndCountry(String street, String city, String postcode,
                                                                String country);

    boolean existsByStreetAndCityAndPostcodeAndCountry(String street, String city, String postcode, String country);
}