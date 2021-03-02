package com.mediscreen.patients.repository;

public enum GenderEnum {
    //Define here the different genders here

    MALE("Male"),
    FEMALE("Female");

    private final String displayValue;

    private GenderEnum(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}