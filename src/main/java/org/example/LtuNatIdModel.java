package org.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LtuNatIdModel {

    private String id;
    private Gender gender;
    private LocalDate birthDate;
    private boolean isValid = true;
    private final List<String> invalidParts = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public boolean isValid() {
        return isValid;
    }

    public List<String> getInvalidParts() {
        return invalidParts;
    }

    public void addInvalidPart(String invalidPart) {
        invalidParts.add(invalidPart);

        if (isValid)
            isValid = false;
    }

}
