package com.mediscreen.patients.services;

import com.mediscreen.patients.model.AddressModel;
import com.mediscreen.patients.repository.AddressRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PatientUTests {

    @Autowired
    private AddressRepository addressRepository;

    public void addressModel() {
        AddressModel addressModel = new AddressModel();
        addressModel.setId(1);
        addressModel.setStreet("StreetTest");
        addressModel.setCity("CityTest");
        addressModel.setPostcode("12345");
        addressModel.setDistrict("DistrictTest");
        addressModel.setState("StateTest");
        addressModel.setCountry("CountryTest");
    }

    @Test
    public void saveUpdateFindDeleteBidShouldPerformTheirActionsAndSucceed() {
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
    }
}