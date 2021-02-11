package com.mediscreen.patients.model;

import com.mediscreen.patients.constraint.GenderEnumPattern;
import com.mediscreen.patients.repository.GenderEnum;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;

@Entity
@Table(name = "patients")
public class PatientModel {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "given_name")
    @NotEmpty(message="Given name cannot be empty")
    @Size(min=2, max=25, message="Given name must be between 2 and 125 characters")
    private String givenName;

    @Column(name = "family_name")
    @NotEmpty(message="Family name cannot be empty")
    @Size(min=2, max=25, message="Family name must be between 2 and 125 characters")
    private String familyName;

    @Past(message = "The date should be a date in the future or now")
    @NotNull(message="Birthdate cannot be empty")
    @Column(name = "birth_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    private LocalDate birthdate;

    //@NotBlank(message = "Gender is mandatory MALE or FEMALE)")
 //   @Size(max = 1, message = "Gender is mandatory (MALE or FEMALE)")
 //   @Pattern(regexp = "^[MALE|FEMALE]{1}$", message = "Must be MALE or FEMALE")
    @GenderEnumPattern(anyOf = { GenderEnum.MALE, GenderEnum.FEMALE }, message = "Must be male or female")
    @Enumerated(EnumType.STRING)
    @Column()
    private GenderEnum gender;

    @Valid
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "patientAddress", cascade = CascadeType.ALL)
    @JoinColumn(name="id",  foreignKey = @ForeignKey(name = "patients_ibfk_1"))
    private AddressModel address;

    @Column(name = "email_address")
    @Email
    private String emailAddress;

    @Column(name = "phone_number")
    @Size(max = 30)
    private String phoneNumber;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public GenderEnum getGender() {
        return gender;
    }

    public void setGender(GenderEnum gender) {
        this.gender = gender;
    }

    public AddressModel getAddress() {
        return address;
    }

    public void setAddress(AddressModel address) {
        this.address = address;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}