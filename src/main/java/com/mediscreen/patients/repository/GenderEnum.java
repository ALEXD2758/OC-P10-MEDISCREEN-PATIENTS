package com.mediscreen.patients.repository;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

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