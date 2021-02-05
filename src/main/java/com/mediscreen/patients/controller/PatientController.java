package com.mediscreen.patients.controller;

import com.mediscreen.patients.service.PatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

@Controller
public class PatientController {

    private Logger logger = LoggerFactory.getLogger(PatientController.class);

    @Autowired
    PatientService patientService;

    /**
     * Get the ModelAndView patient/list
     * Adds attribute "patients" to the model, containing all patients available in DB
     *
     * @param model Model Interface, to add attributes to it
     * @return a string to the address "patient/list", returning the associated view
     * with attribute
     */

    @RequestMapping("/patient/list")
    public String patientList(Model model) {
        model.addAttribute("patients", patientService.getAllPatients());
        logger.info("GET /patient/list : OK");
        return "patient/list";
    }
}
