package com.mediscreen.patients.services;

import com.mediscreen.patients.model.AddressModel;
import com.mediscreen.patients.model.PatientModel;
import com.mediscreen.patients.repository.AddressRepository;
import com.mediscreen.patients.repository.GenderEnum;
import com.mediscreen.patients.repository.PatientRepository;
import com.mediscreen.patients.service.AddressService;
import com.mediscreen.patients.service.PatientService;
import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PatientUTests {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private PatientService patientService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AddressRepository addressRepository;

    public PatientModel patientModel1() {
        AddressModel addressModel1 = new AddressModel();
        addressModel1.setStreet("StreetTest1");
        addressModel1.setCity("CityTest1");
        addressModel1.setPostcode("112345");
        addressModel1.setDistrict("DistrictTest1");
        addressModel1.setState("StateTest1");
        addressModel1.setCountry("CountryTest1");

        LocalDate date = new LocalDate(2020, 01, 01);
        PatientModel patientModel1 = new PatientModel();
        patientModel1.setGivenName("John");
        patientModel1.setFamilyName("Boyd");
        patientModel1.setBirthdate(date);
        patientModel1.setGender(GenderEnum.MALE);
        patientModel1.setAddress(addressModel1);
        patientModel1.setEmailAddress("EmailTest1@email.com");
        patientModel1.setPhoneNumber("004678925899");
        return patientModel1;
    }

    public PatientModel patientModel2() {
        AddressModel addressModel2 = new AddressModel();
        addressModel2.setStreet("StreetTest2");
        addressModel2.setCity("CityTest2");
        addressModel2.setPostcode("212345");
        addressModel2.setDistrict("DistrictTest2");
        addressModel2.setState("StateTest2");
        addressModel2.setCountry("CountryTest2");

        LocalDate date = new LocalDate(2014, 01,01);
        PatientModel patientModel2 = new PatientModel();
        patientModel2.setGivenName("Roger");
        patientModel2.setFamilyName("Patterson");
        patientModel2.setBirthdate(date);
        patientModel2.setGender(GenderEnum.MALE);
        patientModel2.setAddress(addressModel2);
        patientModel2.setEmailAddress("EmailTest2@email.com");
        patientModel2.setPhoneNumber("004678925899");
        return patientModel2;
    }

    @Before
    public void savePatientsToDbBeforeTests() throws SQLException {
        ScriptUtils.executeSqlScript(dataSource.getConnection(), new FileSystemResource("src/test/resources/db_test_scriptV2.sql"));
        patientRepository.deleteAll();
        patientService.savePatient(patientModel1());
        patientService.savePatient(patientModel2());
    }

    @After
    public void deleteAllPatientsAfterTests() {
        patientRepository.deleteAll();
    }

    @Test
    public void getAllPatientsShouldReturnAllPatients() {
        //ARRANGE
        //ACT
        List<PatientModel> listPatient = patientService.getAllPatients();
        //ASSERT
        Assert.assertTrue(listPatient.size() == 2);
        Assert.assertTrue(listPatient.get(0).getGivenName().equals("John"));
        Assert.assertTrue(listPatient.get(0).getAddress().getStreet().equals("StreetTest1"));
    }

    @Test
    public void getAllPatientsByAddressShouldReturnPatientsAtThatAddress() {
        //ARRANGE
        String street = "StreetTest2";
        String city = "CityTest2";
        String country = "CountryTest2";

        //ACT
        List<PatientModel> listPatient =
                patientService.getAllPatientsByAddress(street, city, country);

        //ASSERT
        Assert.assertTrue(listPatient.size() == 1);
        Assert.assertTrue(listPatient.get(0).getGivenName().equals("Roger"));
        Assert.assertTrue(listPatient.get(0).getAddress().getStreet().equals("StreetTest2"));
    }

    @Test
    public void getPatientByIdShouldReturnPatientWithThatId() {
        //ARRANGE
        int id = 1;

        //ACT
        PatientModel patient = patientService.getPatientById(id);

        //ASSERT
        Assert.assertTrue(patient.getGivenName().equals("John"));
        Assert.assertTrue(patient.getGender().equals(GenderEnum.MALE));
        Assert.assertTrue(patient.getAddress().getStreet().equals("StreetTest1"));
    }

    @Test
    public void checkExistentPatientByGivenFamilyNamesBirthDateShouldReturnTrue() {
        //ARRANGE
        String givenName = "Roger";
        String familyName = "Patterson";
        LocalDate birthdate = new LocalDate(2014, 01,01);

        //ACT
        boolean existentPatient = patientService.checkGivenAndFamilyNamesAndBirthDateExist(givenName, familyName,
                birthdate);

        //ASSERT
        Assert.assertEquals(true, existentPatient);
    }

    @Test
    public void checkExistentPatientByIdShouldReturnTrue() {
        //ARRANGE
        int id = 1;

        //ACT
        boolean existentPatientById = patientService.checkIdExists(id);

        //ASSERT
        Assert.assertEquals(true, existentPatientById);
    }

    @Test
    public void deletePatientByIdShouldDeletePatient() {
        //ARRANGE
        int id = 1;

        //ACT
        boolean existentPatientById = patientService.checkIdExists(id);
        patientService.deletePatientById(id);
        Optional<PatientModel> optionalPatientModel = Optional.ofNullable(patientService.getPatientById(id));

        //ASSERT
        Assert.assertFalse(optionalPatientModel.isPresent());
    }

    @Test
    public void savePatientShouldSaveANewPatient() {
        //ARRANGE
        AddressModel addressModel3 = new AddressModel();
        addressModel3.setId(3);
        addressModel3.setStreet("StreetTest3");
        addressModel3.setCity("CityTest3");
        addressModel3.setPostcode("2123445");
        addressModel3.setDistrict("DistrictTest3");
        addressModel3.setState("StateTest3");
        addressModel3.setCountry("CountryTest3");

        LocalDate date = new LocalDate(2018,01,01);
        PatientModel patientModel3 = new PatientModel();
        patientModel3.setGivenName("Test");
        patientModel3.setFamilyName("TestName");
        patientModel3.setBirthdate(date);
        patientModel3.setGender(GenderEnum.FEMALE);
        patientModel3.setEmailAddress("EmailTest3@email.com");
        patientModel3.setPhoneNumber("004678925899");
        patientModel3.setAddress(addressModel3);


        AddressModel address = new AddressModel();
        address.setStreet(patientModel3.getAddress().getStreet());
        address.setCity(patientModel3.getAddress().getCity());
        address.setPostcode(patientModel3.getAddress().getPostcode());
        address.setDistrict(patientModel3.getAddress().getDistrict());
        address.setState(patientModel3.getAddress().getState());
        address.setCountry(patientModel3.getAddress().getCountry());
        patientModel3.setAddress(address);

        //ACT
        PatientModel patientToSave = patientService.savePatient(patientModel3);

        //ASSERT
        Assert.assertNotNull(patientToSave.getId());
        Assert.assertNotNull(patientToSave.getAddress().getStreet());
        Assert.assertEquals("Test", patientToSave.getGivenName());
        Assert.assertEquals("StreetTest3", patientToSave.getAddress().getStreet());
    }

    @Test
    public void updatePatientShouldUpdatePatient() {
        //ARRANGE
  //      AddressModel addressToUpdate = addressRepository.findById(1);


        //ACT
        PatientModel patientModel3 = patientService.getPatientById(1);
        patientModel3.getAddress().setStreet("Street4");
        patientModel3.getAddress().setCity("CityTest4");
        patientModel3.getAddress().setPostcode("1234");
        patientModel3.getAddress().setDistrict("District4");
        patientModel3.getAddress().setState("State4");
        patientModel3.getAddress().setCountry("Country4");
        patientModel3.setFamilyName("UpdatedName");
        PatientModel patientToUpdate = patientService.updatePatient(patientModel3);

        //ASSERT
        Assert.assertEquals("CityTest4", patientToUpdate.getAddress().getCity());
        Assert.assertEquals("UpdatedName", patientToUpdate.getFamilyName());
        Assert.assertEquals("1234", patientToUpdate.getAddress().getPostcode());
    }
}