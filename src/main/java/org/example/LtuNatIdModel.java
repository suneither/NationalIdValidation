package org.example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LtuNatIdModel {

    private long id;
    private Gender gender;
    private Date birthDate;
    private boolean isValid = false;
    private List<String> invalidParts = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public List<String> getInvalidParts() {
        return invalidParts;
    }

    public void setInvalidParts(List<String> invalidParts) {
        this.invalidParts = invalidParts;
    }


}
