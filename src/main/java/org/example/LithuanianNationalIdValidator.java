package org.example;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LithuanianNationalIdValidator extends NationalIdValidator {

    @Override
    public LtuNatIdModel validateID(String id) {
        LtuNatIdModel ltuNatId = new LtuNatIdModel();
        ltuNatId.setId(id);

        validateLength(id, ltuNatId);
        validateGender(id);
        validateBirthDate(id);
        validateControlDigit(id);

        return ltuNatId;
    }

    private boolean validateLength(String id, LtuNatIdModel ltuNatIdModel){

        if(id.length() == 11)
            return true;

        String invalidPart = String.format("Given id length is = %d, but must be 11", id.length());
        ltuNatIdModel.addInvalidPart(invalidPart);
        return false;
    }

    private boolean validateGender(String id){

        int firstDigit = Integer.parseInt(String.valueOf(String.valueOf(id).toCharArray()[0]));

        if(firstDigit == 3 || firstDigit == 5){
            ltuNatId.setGender(Gender.MALE);
            return true;
        }
        else if(firstDigit == 4 || firstDigit == 6){
            ltuNatId.setGender(Gender.FEMALE);
            return true;
        }

        String s = String.format("First digit is = %d, but can be one of these { 3,4,5,6 }", firstDigit);
        ltuNatId.getInvalidParts().add(s);
        return false;
    }

    private boolean validateBirthDate(String id){

        String tempDate = String.valueOf(id).substring(1,7);

        // Very rare exception of Lithuanian national id
        // when 6 date numbers are set to 000000.
        if(tempDate.equals("000000")){
            return true;
        }

        String century = centuryFromIdFirstDigit(Integer.parseInt(String.valueOf(String.valueOf(id).toCharArray()[0])));
        String year = century + tempDate.substring(0,2);

        String month = tempDate.substring(2,4);

        String day = tempDate.substring(4,6);

        String dateAsString = year + "/" + month + "/" + day;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        sdf.setLenient(false);

        Date date;


        try {
            date = sdf.parse(dateAsString);
        } catch (ParseException ex) {
            String s = String.format("Date extracted from id is %s, it does not exist.", dateAsString);
            ltuNatId.getInvalidParts().add(s);
            return false;
        }

        ltuNatId.setBirthDate(date);
        return true;
    }

    private String centuryFromIdFirstDigit(int firstDigit){

        if(firstDigit == 3 || firstDigit == 4)
            return "19";
        else if(firstDigit == 5 || firstDigit == 6)
            return "20";

        return "00";
    }

    private boolean validateControlDigit(String id){

        int lastIdDigit = Integer.parseInt(String.valueOf(String.valueOf(id).toCharArray()[10]));
        int sum = calculateControlDigitSum(id, 1);
        int controlDigit = sum % 11;
        if(controlDigit < 10){
            return lastIdDigit == controlDigit;
        }

        sum = calculateControlDigitSum(id, 3);
        controlDigit = sum % 11;
        if(controlDigit == 10)
            controlDigit = 0;

        return lastIdDigit == controlDigit;
    }

    private int calculateControlDigitSum(long id, int startingMultiplier){
        int sum = 0;
        int multiplier = startingMultiplier;
        char[] idDigits = String.valueOf(id).toCharArray();
        for (int i = 0; i < idDigits.length - 1; i++) {
            sum += Integer.parseInt(String.valueOf(idDigits[i])) * multiplier;
            if(multiplier == 9) multiplier = 0;
            multiplier++;
        }
        return sum;
    }

}
