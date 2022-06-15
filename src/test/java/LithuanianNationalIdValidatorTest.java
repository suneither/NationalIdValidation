import org.example.LithuanianNationalIdValidator;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;

import static org.junit.Assert.*;

public class LithuanianNationalIdValidatorTest {

    private LithuanianNationalIdValidator validator;

    private static final long[] invalidIds = new long[] {
            5_810204_251_5L, 5_560203_834_4L,
            3_690120_915_5L, 3_910804_795_1L

    };

    private static final long[] validIds = new long[] {
            3_440915_785_0L, 3_880505_145_9L
    };

    private long getRandomValidId(){
        return validIds[new Random().nextInt(validIds.length)];
    }

    private long getRandomInvalidId(){
        return invalidIds[new Random().nextInt(invalidIds.length)];
    }

    @Test
    public void validateID_ValidID_True(){
        long id = getRandomValidId();
        
        validator = new LithuanianNationalIdValidator();

        boolean out = validator.validateID(id);
        assertTrue(out);
    }

    @Test
    public void validateID_InvalidID_False(){
        long id = getRandomInvalidId();

        validator = new LithuanianNationalIdValidator();
        boolean out = validator.validateID(id);

        assertFalse(out);
    }

    @Test
    public void validateLength_ValidLength_True(){
        long id = 5_158414_455_5L;

        validator = new LithuanianNationalIdValidator();
        boolean out = validator.validateLength(id);

        assertTrue(out);
    }

    @Test
    public void validateLength_LengthLessThan11_False(){
        long id = 5_158414_45L;

        validator = new LithuanianNationalIdValidator();
        boolean out = validator.validateLength(id);

        assertFalse(out);
    }

    @Test
    public void validateLength_LengthMoreThan11_False(){
        long id = 5_158414_456_355L;

        validator = new LithuanianNationalIdValidator();
        boolean out = validator.validateLength(id);

        assertFalse(out);
    }

    @Test
    public void validateGender_MaleID_True(){
        long id = 5_158414_456_3L;

        validator = new LithuanianNationalIdValidator();
        boolean out = validator.validateGender(id);

        assertTrue(out);
    }

    @Test
    public void validateGender_FemaleID_True(){
        long id = 4_158414_456_3L;

        validator = new LithuanianNationalIdValidator();
        boolean out = validator.validateGender(id);

        assertTrue(out);
    }

    @Test
    public void validateGender_WrongGenderNumber_False(){
        long id = 1_158414_456_3L;

        validator = new LithuanianNationalIdValidator();
        boolean out = validator.validateGender(id);

        assertFalse(out);
    }

    @Test
    public void validateBirthDate_BirthDate000000_True(){
        long id = 3_000000_321_5L;

        validator = new LithuanianNationalIdValidator();
        boolean out = validator.validateBirthDate(id);

        assertTrue(out);
    }

    @Test
    public void validateBirthDate_IDWithValidBirthDate_True(){
        long id = 3_970506_321_5L;

        validator = new LithuanianNationalIdValidator();
        boolean out = validator.validateBirthDate(id);

        assertTrue(out);
    }

    @Test
    public void validateBirthDate_IDWithInvalidBirthDate_False(){
        long id = 3_125051_321_5L;

        validator = new LithuanianNationalIdValidator();
        boolean out = validator.validateBirthDate(id);

        assertFalse(out);
    }

    @Test
    public void centuryFromIdFirstDigit_FirstDigit5or6_21() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        int firstDigit = 6;

        Method method = LithuanianNationalIdValidator.class.getDeclaredMethod("centuryFromIdFirstDigit", int.class);
        method.setAccessible(true);

        validator = new LithuanianNationalIdValidator();

        String century = (String) method.invoke(validator, firstDigit);

        assertEquals("21", century);
    }

    @Test
    public void centuryFromIdFirstDigit_FirstDigit3or4_20() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        int firstDigit = 3;

        Method method = LithuanianNationalIdValidator.class.getDeclaredMethod("centuryFromIdFirstDigit", int.class);
        method.setAccessible(true);

        validator = new LithuanianNationalIdValidator();

        String century = (String) method.invoke(validator, firstDigit);

        assertEquals("20", century);
    }

    @Test
    public void centuryFromIdFirstDigit_FirstDigitWrong_00() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        int firstDigit = 1;

        Method method = LithuanianNationalIdValidator.class.getDeclaredMethod("centuryFromIdFirstDigit", int.class);
        method.setAccessible(true);

        validator = new LithuanianNationalIdValidator();

        String century = (String) method.invoke(validator, firstDigit);

        assertEquals("00", century);
    }

    @Test
    public void validateControlDigit_IDWithCorrectLastDigit_True(){
        validator = new LithuanianNationalIdValidator();
        boolean out = validator.validateControlDigit(getRandomValidId());

        assertTrue(out);
    }

    @Test
    public void validateControlDigit_IDWithIncorrectLastDigit_False(){
        validator = new LithuanianNationalIdValidator();
        boolean out = validator.validateControlDigit(getRandomInvalidId());

        assertFalse(out);
    }

    @Test
    public void calculateControlDigitSum_GivenValidId_ShouldBeEqualsTo283() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        long id = 456_789_456_78L;
        int multiplier = 1;

        Method method = LithuanianNationalIdValidator.class.getDeclaredMethod("calculateControlDigitSum", long.class, int.class);
        method.setAccessible(true);

        validator = new LithuanianNationalIdValidator();
        int sum = (int)method.invoke(validator, id, multiplier);

        assertEquals( 283, sum);
    }
}
