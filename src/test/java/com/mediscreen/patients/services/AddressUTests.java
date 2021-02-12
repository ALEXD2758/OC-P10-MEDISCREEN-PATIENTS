package com.mediscreen.patients.services;

import com.mediscreen.patients.model.AddressModel;
import com.mediscreen.patients.model.PatientModel;
import com.mediscreen.patients.repository.AddressRepository;
import com.mediscreen.patients.repository.GenderEnum;
import com.mediscreen.patients.repository.PatientRepository;
import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AddressUTests {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AddressRepository addressRepository;

    public PatientModel patientModel4() {
        AddressModel addressModel4 = new AddressModel();
        addressModel4.setId(4);
        addressModel4.setStreet("StreetTest1");
        addressModel4.setCity("CityTest1");
        addressModel4.setPostcode("112345");
        addressModel4.setDistrict("DistrictTest1");
        addressModel4.setState("StateTest1");
        addressModel4.setCountry("CountryTest1");

        LocalDate date = new LocalDate(2020/01/01);
        PatientModel patientModel4 = new PatientModel();
        patientModel4.setId(4);
        patientModel4.setGivenName("John");
        patientModel4.setFamilyName("Boyd");
        patientModel4.setBirthdate(date);
        patientModel4.setGender(GenderEnum.MALE);
        patientModel4.setAddress(addressModel4);
        patientModel4.setEmailAddress("EmailTest1@email.com");
        patientModel4.setPhoneNumber("004678925899");
        return patientModel4;
    }

    public PatientModel patientModel5() {
        AddressModel addressModel5 = new AddressModel();
        addressModel5.setId(5);
        addressModel5.setStreet("StreetTest2");
        addressModel5.setCity("CityTest2");
        addressModel5.setPostcode("212345");
        addressModel5.setDistrict("DistrictTest2");
        addressModel5.setState("StateTest2");
        addressModel5.setCountry("CountryTest2");

        LocalDate date = new LocalDate(2019/01/01);
        PatientModel patientModel5 = new PatientModel();
        patientModel5.setId(5);
        patientModel5.setGivenName("Roger");
        patientModel5.setFamilyName("Patterson");
        patientModel5.setBirthdate(date);
        patientModel5.setGender(GenderEnum.MALE);
        patientModel5.setAddress(addressModel5);
        patientModel5.setEmailAddress("EmailTest2@email.com");
        patientModel5.setPhoneNumber("004678925899");
        return patientModel5;
    }

    public PatientModel patientModel6() {
        AddressModel addressModel6 = new AddressModel();
        addressModel6.setId(6);
        addressModel6.setStreet("StreetTest2");
        addressModel6.setCity("CityTest2");
        addressModel6.setPostcode("212345");
        addressModel6.setDistrict("DistrictTest2");
        addressModel6.setState("StateTest2");
        addressModel6.setCountry("CountryTest2");

        LocalDate date = new LocalDate(2018/01/01);
        PatientModel patientModel6 = new PatientModel();
        patientModel6.setId(6);
        patientModel6.setGivenName("David");
        patientModel6.setFamilyName("Patterson");
        patientModel6.setBirthdate(date);
        patientModel6.setGender(GenderEnum.MALE);
        patientModel6.setAddress(addressModel6);
        patientModel6.setEmailAddress("EmailTest3@email.com");
        patientModel6.setPhoneNumber("0045899");
        return patientModel6;
    }

    @Before
    public void savePatientsToDbBeforeTests() {
        patientRepository.save(patientModel4());
        patientRepository.save(patientModel5());
        patientRepository.save(patientModel6());
    }

    @AfterAll
    public void deleteAllPatients() {
        patientRepository.deleteAll();
    }

    @Test
    public void getAddressByIdShouldReturnAddressWithThatId() {
        //ARRANGE
        int id = 4;

        //ACT
        PatientModel patient = patientRepository.findById(id);

        //ASSERT
        Assert.assertTrue(patient.getGivenName().equals("Roger"));
        Assert.assertTrue(patient.getGender().equals(GenderEnum.MALE));
        Assert.assertTrue(patient.getAddress().getStreet().equals("StreetTest1"));
    }

    @Test
    public void getAllPatientsWithExistentAddressShouldReturnPatientsAtThatAddress() {
        //ARRANGE
        String street = "StreetTest2";
        String city = "CityTest2";
        String country = "CountryTest2";

        //ACT
        List<PatientModel> listPatient =
                patientRepository.findAllByAddressStreetAndAddressCityAndAddressCountry(street, city, country);

        //ASSERT
        Assert.assertTrue(listPatient.size() == 2);
        Assert.assertTrue(listPatient.get(0).getGivenName().equals("Roger"));
        Assert.assertTrue(listPatient.get(1).getGivenName().equals("David"));
        Assert.assertTrue(listPatient.get(0).getAddress().getStreet().equals("StreetTest2"));
    }
}