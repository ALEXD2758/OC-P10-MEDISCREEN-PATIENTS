package com.mediscreen.patients.services;

import com.mediscreen.patients.model.AddressModel;
import com.mediscreen.patients.model.PatientModel;
import com.mediscreen.patients.repository.AddressRepository;
import com.mediscreen.patients.repository.GenderEnum;
import com.mediscreen.patients.repository.PatientRepository;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
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
public class PatientUTests {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AddressRepository addressRepository;

    public PatientModel patientModel1() {
        AddressModel addressModel1 = new AddressModel();
        addressModel1.setId(1);
        addressModel1.setStreet("StreetTest1");
        addressModel1.setCity("CityTest1");
        addressModel1.setPostcode("112345");
        addressModel1.setDistrict("DistrictTest1");
        addressModel1.setState("StateTest1");
        addressModel1.setCountry("CountryTest1");

        LocalDate date = new LocalDate(2020/01/01);
        PatientModel patientModel1 = new PatientModel();
        patientModel1.setId(1);
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
        addressModel2.setId(2);
        addressModel2.setStreet("StreetTest2");
        addressModel2.setCity("CityTest2");
        addressModel2.setPostcode("212345");
        addressModel2.setDistrict("DistrictTest2");
        addressModel2.setState("StateTest2");
        addressModel2.setCountry("CountryTest2");

        LocalDate date = new LocalDate(2019/01/01);
        PatientModel patientModel2 = new PatientModel();
        patientModel2.setId(1);
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
    public void savePatientsToDbBeforeTests() {
        patientRepository.save(patientModel1());
        patientRepository.save(patientModel2());
    }

    @AfterAll
    public void deleteAllPatients() {
        patientRepository.deleteAll();
    }

    @Test
    public void getAllPatientsShouldReturnAllPatients() {
        //ARRANGE
        //ACT
        List<PatientModel> listPatient = patientRepository.findAll();
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
                patientRepository.findAllByAddressStreetAndAddressCityAndAddressCountry(street, city, country);

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
        PatientModel patient = patientRepository.findById(id);

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
        LocalDate birthdate = new LocalDate(2019/01/01);

        //ACT
        boolean existentPatient = patientRepository.existsByGivenNameAndFamilyNameAndBirthdate(givenName, familyName,
                birthdate);

        //ASSERT
        Assert.assertEquals("TRUE", existentPatient);
    }

    @Test
    public void checkExistentPatientByIdShouldReturnTrue() {
        //ARRANGE
        //ARRANGE
        int id = 1;

        //ACT
        boolean existentPatientById = patientRepository.existsById(id);

        //ASSERT
        Assert.assertEquals("TRUE", existentPatientById);
    }

    @Test
    public void deletePatientByIdShouldDeletePatient() {
        //ARRANGE
        int id = 1;

        //ACT
        boolean existentPatientById = patientRepository.existsById(id);
        patientRepository.deleteById(id);
        Optional<PatientModel> optionalPatientModel = Optional.ofNullable(patientRepository.findById(id));

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

        LocalDate date = new LocalDate(2018/01/01);
        PatientModel patientModel3 = new PatientModel();
        patientModel3.setId(3);
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
        PatientModel patientToSave = patientRepository.save(patientModel3);

        //ASSERT
        Assert.assertNotNull(patientToSave.getId());
        Assert.assertNotNull(patientToSave.getAddress().getStreet());
        Assert.assertEquals("Test", patientToSave.getGivenName());
        Assert.assertEquals("StreetTest3", patientToSave.getAddress().getStreet());
    }

    @Test
    public void updatePatientShouldUpdatePatient() {
        //ARRANGE
        AddressModel addressToUpdate = addressRepository.findById(3);
        addressToUpdate.setStreet("Street4");
        addressToUpdate.setCity("CityTest4");
        addressToUpdate.setPostcode("1234");
        addressToUpdate.setDistrict("District4");
        addressToUpdate.setState("State4");
        addressToUpdate.setCountry("Country4");

        //ACT
        PatientModel patientModel3 = patientRepository.findById(3);
        patientModel3.setAddress(addressToUpdate);
        patientModel3.setFamilyName("UpdatedName");
        PatientModel patientToUpdate = patientRepository.save(patientModel3);

        //ASSERT
        Assert.assertEquals("CityTest4", patientToUpdate.getAddress().getCity());
        Assert.assertEquals("UpdatedName", patientToUpdate.getFamilyName());
        Assert.assertEquals("1234", patientToUpdate.getAddress().getPostcode());
    }

 //   @Test
 //   public void saveUpdateFindDeleteBidShouldPerformTheirActionsAndSucceed() {
/*
        // Save
        bid = bidListRepository.save(bid);
        Assert.assertNotNull(bid.getBidListId());
        Assert.assertEquals(bid.getBidQuantity(), 10d, 10d);

        // Update
        bid.setBidQuantity(20d);
        bid = bidListRepository.save(bid);
        Assert.assertEquals(bid.getBidQuantity(), 20d, 20d);

        // Find
        List<BidListModel> listResult = bidListRepository.findAll();
        Assert.assertTrue(listResult.size() > 0);

        BidListModel bidModel = bidListRepository.findByBidListId(bid.getBidListId());
        Assert.assertEquals(bidModel.getAccount(), "Account Test", "Account Test");

        // Delete
        int id = bid.getBidListId();
        bidListRepository.delete(bid);
        Optional<BidListModel> BidListModel = bidListRepository.findById(id);
        Assert.assertFalse(BidListModel.isPresent());

 */
  //  }
}