package com.mediscreen.patients.model;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

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
    @NotEmpty(message="Birthdate cannot be empty")
    @Column(name = "birth_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentInstantAsTimestamp")
    private LocalDate birthdate;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "patientSex")
    @JoinColumn(name="id", foreignKey = @ForeignKey(name = "patients_ibfk_1"))
    private SexModel sex;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "patientAddress")
    @JoinColumn(name="id", foreignKey = @ForeignKey(name = "patients_ibfk_2"))
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

    public SexModel getSex() {
        return sex;
    }

    public void setSexModel(SexModel sexModel) {
        this.sex = sex;
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