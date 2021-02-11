package com.mediscreen.patients.controllers;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
public class PatientControllerITTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webContext;

 //   @MockBean
  //  BidListService bidListService;

    @Before
    public void setupMockmvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
    }
/*
    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void getRequestBidListListViewShouldReturnSuccess() throws Exception {
        //1. Setup

        BidListModel bidModel = new BidListModel();
        bidModel.setBidListId(1);
        bidModel.setAccount("Account Test");
        bidModel.setType("Type Test");
        bidModel.setBidQuantity(10);

        List<BidListModel> bidList = new ArrayList<>();
        bidList.add(bidModel);

        doReturn(bidList)
                .when(bidListService)
                .getAllBids();

        //2. Act
        mockMvc.perform(get("/bidList/list"))
        //3. Assert
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("bidList/list"))
                .andExpect(model().attributeExists("bidList"))
                .andReturn();
        assertTrue(bidList.get(0).getAccount().equals("Account Test"));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void getRequestBidListAddViewShouldReturnSuccess() throws Exception {
        //1. Setup

        //2. Act
        mockMvc.perform(get("/bidList/add"))
        //3. Assert
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("bidList/add"))
                .andExpect(model().attributeExists("bidList"))
                .andReturn();
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void postRequestBidListValidateShouldReturnSuccess() throws Exception {
        //1. Setup

        BidListModel bidModel = new BidListModel();
        bidModel.setBidListId(1);
        bidModel.setAccount("Account Test");
        bidModel.setType("Type Test");
        bidModel.setBidQuantity(10);

        List<BidListModel> bidList = new ArrayList<>();
        bidList.add(bidModel);

        doNothing()
                .when(bidListService)
                .saveBid(bidModel);

        doReturn(bidList)
                .when(bidListService)
                .getAllBids();
        //2. Act
        mockMvc.perform(post("/bidList/validate")
                 .flashAttr("successSaveMessage", "Your bid was successfully added")
                .param("bidListId", "1")
                .param("account", "Account Test")
                .param("type", "Type Test")
                .param("bidQuantity", "10"))
        //3. Assert
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/bidList/list"))
                .andExpect(flash().attributeExists("successSaveMessage"))
                .andReturn();
        assertTrue(bidList.get(0).getAccount().equals("Account Test"));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void getRequestBidListUpdateIdShouldReturnSuccess() throws Exception {
        //1. Setup

        BidListModel bidModel = new BidListModel();
        bidModel.setBidListId(1);
        bidModel.setAccount("Account Test");
        bidModel.setType("Type Test");
        bidModel.setBidQuantity(10);

        doReturn(true)
                .when(bidListService)
                .checkIfIdExists(bidModel.getBidListId());

        doReturn(bidModel)
                .when(bidListService)
                .getBidByBidListId(bidModel.getBidListId());
        //2. Act
        mockMvc.perform(get("/bidList/update/{id}", "1"))
        //3. Assert
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("bidList/update"))
                .andExpect(model().attributeExists("bidList"))
                .andReturn();
        assertTrue(bidModel.getAccount().equals("Account Test"));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void postRequestBidListUpdateIdShouldReturnSuccess() throws Exception {
        //1. Setup

        BidListModel bidModel = new BidListModel();
        bidModel.setBidListId(1);
        bidModel.setAccount("Account Test");
        bidModel.setType("Type Test");
        bidModel.setBidQuantity(10);

        List<BidListModel> bidList = new ArrayList<>();
        bidList.add(bidModel);

        doReturn(true)
                .when(bidListService)
                .checkIfIdExists(bidModel.getBidListId());
        doNothing()
                .when(bidListService)
                .saveBid(bidModel);

        doReturn(bidList)
                .when(bidListService)
                .getAllBids();

        //2. Act
        mockMvc.perform(post("/bidList/update/{id}", "1")
                .flashAttr("successUpdateMessage", "Your bid was successfully updated")
                .param("bidListId", "1")
                .param("account", "Account Test")
                .param("type", "Type Test")
                .param("bidQuantity", "10"))
        //3. Assert
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/bidList/list"))
                .andExpect(flash().attributeExists("successUpdateMessage"))
                .andReturn();
        assertTrue(bidList.get(0).getAccount().equals("Account Test"));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void getRequestBidListDeleteIdShouldReturnSuccess() throws Exception {
        //1. Setup

        BidListModel bidModel = new BidListModel();
        bidModel.setBidListId(1);
        bidModel.setAccount("Account Test");
        bidModel.setType("Type Test");
        bidModel.setBidQuantity(10);

        List<BidListModel> bidList = new ArrayList<>();
        bidList.add(bidModel);

        doReturn(true)
                .when(bidListService)
                .checkIfIdExists(bidModel.getBidListId());

        doNothing()
                .when(bidListService)
                .deleteBidById(bidModel.getBidListId());

        doReturn(bidList)
                .when(bidListService)
                .getAllBids();
        //2. Act
        mockMvc.perform(get("/bidList/delete/{id}", "1")
                .flashAttr("successDeleteMessage", "Your bid was successfully deleted"))
        //3. Assert
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/bidList/list"))
                .andExpect(flash().attributeExists("successDeleteMessage"))
                .andReturn();
        assertTrue(bidList.get(0).getAccount().equals("Account Test"));
    }

 */
}