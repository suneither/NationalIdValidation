package org.example;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LithuanianNationalIdValidator extends NationalIdValidator {

    private static final String UNKNOWN_CENTURY = "00";

    @Override
    public LtuNatIdModel validateID(String id) {

        if (id == null) return null;

        LtuNatIdModel ltuNatId = new LtuNatIdModel();
        ltuNatId.setId(id);

        ltuNatId = validateLength(id, ltuNatId);
        if (ltuNatId.isValid()) {
            ltuNatId = validateGender(id, ltuNatId);
            ltuNatId = validateBirthDate(id, ltuNatId);
            ltuNatId = validateControlDigit(id, ltuNatId);
        }

        return ltuNatId;
    }

    private LtuNatIdModel validateLength(String id, LtuNatIdModel ltuNatIdModel) {

        if (id.length() == 11)
            return ltuNatIdModel;

        String invalidPart = String.format("Given id length is = %d, must be 11.", id.length());
        ltuNatIdModel.addInvalidPart(invalidPart);
        return ltuNatIdModel;
    }

    private LtuNatIdModel validateGender(String id, LtuNatIdModel ltuNatIdModel) {

        String firstDigit = id.substring(0, 1);

        if (firstDigit.equals("3") || firstDigit.equals("5")) {
            ltuNatIdModel.setGender(Gender.MALE);
            return ltuNatIdModel;
        } else if (firstDigit.equals("4") || firstDigit.equals("6")) {
            ltuNatIdModel.setGender(Gender.FEMALE);
            return ltuNatIdModel;
        }

        String invalidPart = String.format("First digit is = %s, but can be one of these { 3,4,5,6 }.", firstDigit);
        ltuNatIdModel.addInvalidPart(invalidPart);
        return ltuNatIdModel;
    }

    private LtuNatIdModel validateBirthDate(String id, LtuNatIdModel ltuNatIdModel) {

        String tempDate = id.substring(1, 7);

        // Very rare exception of Lithuanian national id
        // when 6 date numbers are set to 000000.
        if (tempDate.equals("000000")) {
            return ltuNatIdModel;
        }

        String century = centuryFromIdFirstDigit(id.substring(0, 1));
        String year = century + tempDate.substring(0, 2);

        String month = tempDate.substring(2, 4);

        String day = tempDate.substring(4, 6);

        String dateAsString = year + "/" + month + "/" + day;

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");

        LocalDate date;
        try {
            date = LocalDate.parse(dateAsString, dtf);
        } catch (DateTimeParseException ex) {
            String invalidPart = String.format("Date extracted from id is %s, it does not exist.", dateAsString);
            ltuNatIdModel.addInvalidPart(invalidPart);
            return ltuNatIdModel;
        }

        ltuNatIdModel.setBirthDate(date);
        return ltuNatIdModel;
    }

    private String centuryFromIdFirstDigit(String firstDigit) {

        if (firstDigit.equals("3") || firstDigit.equals("4"))
            return "19";
        else if (firstDigit.equals("5") || firstDigit.equals("6"))
            return "20";

        return UNKNOWN_CENTURY;
    }

    private LtuNatIdModel validateControlDigit(String id, LtuNatIdModel ltuNatIdModel) {

        int lastIdDigit = 0;
        try {
            lastIdDigit = Integer.parseInt(id.substring(10, 10));
        } catch (NumberFormatException ex) {
            String invalidPart = String.format("Last id digit is %d, it has to be number.", lastIdDigit);
            ltuNatIdModel.addInvalidPart(invalidPart);
            return ltuNatIdModel;
        }

        int sum = calculateControlDigitSum(lastIdDigit, 1);
        int controlDigit = sum % 11;
        if (controlDigit < 10) {
            if (lastIdDigit != controlDigit) {
                String invalidPart = String.format("Control digit is %d, it has to match last id digit %d", controlDigit, lastIdDigit);
                ltuNatIdModel.addInvalidPart(invalidPart);
            }
            return ltuNatIdModel;
        }

        sum = calculateControlDigitSum(lastIdDigit, 3);
        controlDigit = sum % 11;
        if (controlDigit == 10)
            controlDigit = 0;

        if (lastIdDigit != controlDigit) {
            String invalidPart = String.format("Control digit is %d, it has to match last id digit %d", controlDigit, lastIdDigit);
            ltuNatIdModel.addInvalidPart(invalidPart);
        }

        return ltuNatIdModel;
    }

    private int calculateControlDigitSum(long id, int startingMultiplier) {
        int sum = 0;
        int multiplier = startingMultiplier;
        char[] idDigits = String.valueOf(id).toCharArray();
        for (int i = 0; i < idDigits.length - 1; i++) {
            sum += Integer.parseInt(String.valueOf(idDigits[i])) * multiplier;
            if (multiplier == 9) multiplier = 0;
            multiplier++;
        }
        return sum;
    }

}
