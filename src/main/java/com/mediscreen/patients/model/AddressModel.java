package com.mediscreen.patients.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "address")
public class AddressModel {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

 //   @OneToOne(fetch = FetchType.LAZY)
 //   @PrimaryKeyJoinColumn(name = "id")
 //   private PatientModel patientAddress;

    @NotEmpty(message="Street cannot be empty")
    @Column(name="street")
    private String street;

    @NotEmpty(message="City cannot be empty")
    @Column(name="city")
    private String city;

    @NotEmpty(message="Postcode cannot be empty")
    @Column(name="postcode")
    private String postcode;

    @NotEmpty(message="District cannot be empty")
    @Column(name="district")
    private String district;

    @NotEmpty(message="State cannot be empty")
    @Column(name="state")
    private String state;

    @NotEmpty(message="Country cannot be empty")
    @Column(name="country")
    private String country;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}