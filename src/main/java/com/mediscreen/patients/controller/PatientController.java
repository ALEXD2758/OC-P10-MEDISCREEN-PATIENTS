package com.mediscreen.patients.controller;

import com.mediscreen.patients.model.PatientModel;
import com.mediscreen.patients.service.AddressService;
import com.mediscreen.patients.service.PatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class PatientController {

    private final Logger logger = LoggerFactory.getLogger(PatientController.class);

    @Autowired
    PatientService patientService;

    @Autowired
    AddressService addressService;

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

    /**
     * HTTP GET request to get the patient add.html page with the attribute patient
     *
     * @param model Model Interface, to add attributes to it
     * @returna string to the address "patient/add", returning the associated view
     * with attribute
     */
    @GetMapping("/patient/add")
    public String patientAdd(Model model) {
        model.addAttribute("patient", new PatientModel());
        logger.info("GET /patient/add : OK");
        return "patient/add";
    }

    /**
     * HTML POST request to add a new patient if it doesn't exist
     * Add redirect attributes messages: errorSaveMessage if the patient already exist
     *                                   successSaveMessage if the patient is new
     *g
     * @param patientToCreate the PatientModel with annotation @Valid (for the possible constraints)
     * @param result to represent binding results
     * @param ra the RedirectAttributes to redirect attributes in redirect
     * @return a string to the address "patient/add" or patient/list, returning the associated view,
     * with attributes
     */
    @PostMapping("/patient/add/validate")
    public String patientAddValidate(@Valid @ModelAttribute("patientToCreate") PatientModel patientToCreate,
                                     BindingResult result, RedirectAttributes ra) {
        if (!result.hasErrors()) {
            if (patientService.checkGivenAndFamilyNamesAndBirthDateExist(patientToCreate.getGivenName(),
                    patientToCreate.getFamilyName(), patientToCreate.getBirthdate()) == true) {
                logger.info("/patient/add/validate : Patient already exist");
                ra.addFlashAttribute("ErrorPatientExistentMessage", "Patient already exist");
                return "redirect:/patient/add";
            }
            else if(patientService.checkGivenAndFamilyNamesAndBirthDateExist(patientToCreate.getGivenName(),
                    patientToCreate.getFamilyName(), patientToCreate.getBirthdate()) == false) {
                if (addressService.getAllPatientsWithExistentAddress(patientToCreate.getAddress().getStreet(),
                    patientToCreate.getAddress().getCity(), patientToCreate.getAddress().getPostcode(),
                    patientToCreate.getAddress().getCountry()).size() > 0) {
                    List<PatientModel> listPatientsAlreadyAtThatAddress =
                            patientService.getAllPatientsByAddress(patientToCreate.getAddress().getStreet(),
                                    patientToCreate.getAddress().getCity(), patientToCreate.getAddress().getCountry());
                    ra.addFlashAttribute("patientListAtAddress",
                            listPatientsAlreadyAtThatAddress);
                    ra.addFlashAttribute("patientToCreate",
                            patientToCreate);
                    return "redirect:/patient/add/confirmation";
                    }
                else if (addressService.getAllPatientsWithExistentAddress(patientToCreate.getAddress().getStreet(),
                        patientToCreate.getAddress().getCity(), patientToCreate.getAddress().getPostcode(),
                        patientToCreate.getAddress().getCountry()).size() == 0) {
                    patientService.savePatient(patientToCreate);
                    logger.info("POST /patient/add/validate : OK");
                    ra.addFlashAttribute("successSaveMessage", "Patient was successfully added");
                    return "redirect:/patient/list";
                }
            }
        }
        return "redirect:/patient/list";
    }
    // --------------------------------------------------------------------------------------------------------------
    /**
     * HTTP GET request to get the patient add.html page with the attribute patient
     *
     * @returna string to the address "patient/add", returning the associated view
     * with attribute
     */
    @GetMapping("/patient/add/confirmation")
    public String patientAddConfirmation() {
        logger.info("GET /patient/add/confirmation : OK");
        return "patient/confirmationAdd";
    }

    /**
     * HTML POST request to add a new patient if it doesn't exist
     * Add redirect attributes messages: errorSaveMessage if the patient already exist
     *                                   successSaveMessage if the patient is new
     *
     * @param patientToCreate the PatientModel with annotation @Valid (for the possible constraints)
     * @param ra the RedirectAttributes to redirect attributes in redirect
     * @return a string to the address "patient/add" or patient/list, returning the associated view,
     * with attributes
     */
    @PostMapping("/patient/add/confirmation/validate")
    public String patientAddConfirmationValidate(@Valid PatientModel patientToCreate, BindingResult result,
            RedirectAttributes ra) {
        if (!result.hasErrors()) {
            patientService.savePatient(patientToCreate);
            ra.addFlashAttribute("successSaveMessage", "Patient was successfully added");
            return "redirect:/patient/list";
        }
        if (result.hasErrors()) {
                logger.info("/patient/add/confirmation/validate : NOK - Request went wrong");
        }
        return "redirect:/patient/list";
    }

    /**
     * Get the view patient/update with the chosen patient in a model attribute
     * with the associated data of the chosen ID
     * Add attribute patient to the model
     *
     * @param id the int of the patient id chosen
     * @param model the Model Interface, to add attributes to it
     * @return a string to the address "patient/update", returning the associated view
     * with attribute (if no Exception)
     */
    @GetMapping("/patient/update/{id}")
    public String patientUpdate(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        try {
            if (patientService.checkIdExists(id) == false) {
                ra.addFlashAttribute("ErrorPatientIdMessage", "Patient ID doesn't exist");
                logger.info("GET /patient/update : Non existent id");
                return "redirect:/patient/list";
            }
        } catch (Exception e) {
            ra.addFlashAttribute("InvalidPatientIdMessage", "Invalid patient ID");
            logger.info("/patient/update : NOK " + "Invalid patient ID " + id);
            return "redirect:/patient/list";
        }
        model.addAttribute("patient", patientService.getPatientById(id));
        logger.info("GET /patient/update : OK");
        return "patient/update";
    }

    /**
     * Update existing patient to the table patients if BindingResult has no errors
     * Add Flash Attribute with success message
     * Add attribute patient to the model, containing all patients available in DB
     *
     * @param patient the PatientModel with annotation @Valid (for the possible constraints)
     * @param result to represent binding results
     * @param ra the RedirectAttributes to redirect attributes in redirect
     * @return a string to the address "patient/list", returning the associated view,
     * with attributes
     */
    @PostMapping("/patient/update/{id}")
    public String patientPostUpdate(@PathVariable("id") Integer id,
                                  @Valid @ModelAttribute("patient") PatientModel patient,
                            BindingResult result, RedirectAttributes ra) {
        if (patientService.checkIdExists(id) == false) {
            ra.addFlashAttribute("ErrorPatientIdMessage", "Patient ID doesn't exist");
            logger.info("GET /patient/update : Non existent id");
            return "redirect:/patient/list";
        }
        if (!result.hasErrors()) {
            patientService.updatePatient(patient);
            ra.addFlashAttribute("successUpdateMessage", "Your patient was successfully updated");
            logger.info("POST /patient/update : OK");
            return "redirect:/patient/list";
        }
        logger.info("POST /patient/update : NOK");
        return "/patient/add";
    }

    /**
     * Delete existing patient from the table patients
     * Add Flash Attribute with success message
     * Add attribute patient to the model, containing all Bids available in DB
     *
     * @param id the Integer of the patient ID chosen
     * @param model the Model Interface, to add attributes to it
     * @param ra the RedirectAttributes to redirect attributes in redirect
     * @return a string to the address "patient/list", returning the associated view,
     * with attributes
     */
    @GetMapping("/patient/delete/{id}")
    public String patientDelete(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        try {
            if (patientService.checkIdExists(id) == false) {
                ra.addFlashAttribute("ErrorPatientIdMessage", "Patient ID doesn't exist");
                logger.info("GET /patient/delete : Non existent patient id");
                return "redirect:/patient/list";
            }
            patientService.deletePatientById(id);
            model.addAttribute("patient", patientService.getAllPatients());
            ra.addFlashAttribute("successDeleteMessage", "This patient was successfully deleted");
            logger.info("/patient/delete : OK");

        } catch (Exception e) {
            ra.addFlashAttribute("errorDeleteMessage", "Error during deletion of the patient");
            logger.info("/patient/delete : NOK " + "Invalid patient ID " + id);
        }
        return "redirect:/patient/list";
    }
}