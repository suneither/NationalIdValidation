package org.example;

public abstract class NationalIdValidator {

    public abstract boolean validateID(long id);

    public abstract boolean validateLength(long id);

    public abstract boolean validateGender(long id);

    public abstract boolean validateBirthDate(long id);
}
