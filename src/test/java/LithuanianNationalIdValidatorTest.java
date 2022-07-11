import org.example.LithuanianNationalIdValidator;
import org.example.LtuNatIdModel;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class LithuanianNationalIdValidatorTest {

    private LithuanianNationalIdValidator validator;

    LithuanianNationalIdValidatorTest() {
        validator = new LithuanianNationalIdValidator();
    }

    private static final String[] invalidIds = new String[]{
            "58102042515", "55602038344",
            "36901209155", "39108047951"
    };

    private static final String[] validIds = new String[]{
            "34409157850", "38805051459"
    };

    private String getRandomValidId() {
        return validIds[new Random().nextInt(validIds.length)];
    }

    private String getRandomInvalidId() {
        return invalidIds[new Random().nextInt(invalidIds.length)];
    }

    @Test
    public void validateID_ValidID_True() {
        String id = getRandomValidId();

        LtuNatIdModel ltuNatIdModel = validator.validateID(id);

        assertTrue(ltuNatIdModel.isValid());
    }

    @Test
    public void validateID_InvalidID_False() {
        String id = getRandomInvalidId();

        LtuNatIdModel ltuNatIdModel = validator.validateID(id);

        assertFalse(ltuNatIdModel.isValid());
    }

    @Test
    public void validateLength_ValidLength_True() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String id = "51584144555";
        LtuNatIdModel ltuNatIdModel = new LtuNatIdModel();

        Method method = LithuanianNationalIdValidator.class.getDeclaredMethod("validateLength", String.class, LtuNatIdModel.class);
        method.setAccessible(true);

        ltuNatIdModel = (LtuNatIdModel) method.invoke(validator, id, ltuNatIdModel);

        assertTrue(ltuNatIdModel.isValid());
    }

    @Test
    public void validateLength_LengthLessThan11_False() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String id = "515841445";
        LtuNatIdModel ltuNatIdModel = new LtuNatIdModel();

        Method method = LithuanianNationalIdValidator.class.getDeclaredMethod("validateLength", String.class, LtuNatIdModel.class);
        method.setAccessible(true);

        ltuNatIdModel = (LtuNatIdModel) method.invoke(validator, id, ltuNatIdModel);

        assertFalse(ltuNatIdModel.isValid());
    }

    @Test
    public void validateLength_LengthMoreThan11_False() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String id = "5158414456355";
        LtuNatIdModel ltuNatIdModel = new LtuNatIdModel();

        Method method = LithuanianNationalIdValidator.class.getDeclaredMethod("validateLength", String.class, LtuNatIdModel.class);
        method.setAccessible(true);

        ltuNatIdModel = (LtuNatIdModel) method.invoke(validator, id, ltuNatIdModel);

        assertFalse(ltuNatIdModel.isValid());
    }

    @Test
    public void validateGender_MaleID_True() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String id = "51584144563";
        LtuNatIdModel ltuNatIdModel = new LtuNatIdModel();

        Method method = LithuanianNationalIdValidator.class.getDeclaredMethod("validateGender", String.class, LtuNatIdModel.class);
        method.setAccessible(true);

        ltuNatIdModel = (LtuNatIdModel) method.invoke(validator, id, ltuNatIdModel);

        assertTrue(ltuNatIdModel.isValid());
    }

    @Test
    public void validateGender_FemaleID_True() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String id = "41584144563";

        LtuNatIdModel ltuNatIdModel = new LtuNatIdModel();

        Method method = LithuanianNationalIdValidator.class.getDeclaredMethod("validateGender", String.class, LtuNatIdModel.class);
        method.setAccessible(true);

        ltuNatIdModel = (LtuNatIdModel) method.invoke(validator, id, ltuNatIdModel);

        assertTrue(ltuNatIdModel.isValid());
    }

    @Test
    public void validateGender_WrongGenderNumber_False() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String id = "11584144563";
        LtuNatIdModel ltuNatIdModel = new LtuNatIdModel();

        Method method = LithuanianNationalIdValidator.class.getDeclaredMethod("validateGender", String.class, LtuNatIdModel.class);
        method.setAccessible(true);

        ltuNatIdModel = (LtuNatIdModel) method.invoke(validator, id, ltuNatIdModel);

        assertFalse(ltuNatIdModel.isValid());
    }

    @Test
    public void validateBirthDate_BirthDate000000_True() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String id = "30000003215";
        LtuNatIdModel ltuNatIdModel = new LtuNatIdModel();

        Method method = LithuanianNationalIdValidator.class.getDeclaredMethod("validateBirthDate", String.class, LtuNatIdModel.class);
        method.setAccessible(true);

        ltuNatIdModel = (LtuNatIdModel) method.invoke(validator, id, ltuNatIdModel);

        assertTrue(ltuNatIdModel.isValid());
    }

    @Test
    public void validateBirthDate_IDWithValidBirthDate_True() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String id = "39705063215";
        LtuNatIdModel ltuNatIdModel = new LtuNatIdModel();

        Method method = LithuanianNationalIdValidator.class.getDeclaredMethod("validateBirthDate", String.class, LtuNatIdModel.class);
        method.setAccessible(true);

        ltuNatIdModel = (LtuNatIdModel) method.invoke(validator, id, ltuNatIdModel);

        assertTrue(ltuNatIdModel.isValid());
    }

    @Test
    public void validateBirthDate_IDWithInvalidBirthDate_False() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String id = "31250513215";
        LtuNatIdModel ltuNatIdModel = new LtuNatIdModel();

        Method method = LithuanianNationalIdValidator.class.getDeclaredMethod("validateBirthDate", String.class, LtuNatIdModel.class);
        method.setAccessible(true);

        ltuNatIdModel = (LtuNatIdModel) method.invoke(validator, id, ltuNatIdModel);

        assertFalse(ltuNatIdModel.isValid());
    }

    @Test
    public void centuryFromIdFirstDigit_FirstDigit5or6_21() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String firstDigit = "6";

        Method method = LithuanianNationalIdValidator.class.getDeclaredMethod("centuryFromIdFirstDigit", String.class);
        method.setAccessible(true);

        String century = (String) method.invoke(validator, firstDigit);

        assertEquals("20", century);
    }

    @Test
    public void centuryFromIdFirstDigit_FirstDigit3or4_20() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String firstDigit = "3";

        Method method = LithuanianNationalIdValidator.class.getDeclaredMethod("centuryFromIdFirstDigit", String.class);
        method.setAccessible(true);

        String century = (String) method.invoke(validator, firstDigit);

        assertEquals("19", century);
    }

    @Test
    public void centuryFromIdFirstDigit_FirstDigitWrong_00() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String firstDigit = "1";

        Method method = LithuanianNationalIdValidator.class.getDeclaredMethod("centuryFromIdFirstDigit", String.class);
        method.setAccessible(true);

        String century = (String) method.invoke(validator, firstDigit);

        assertEquals("00", century);
    }

    @Test
    public void validateControlDigit_IDWithCorrectLastDigit_True() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String id = getRandomValidId();
        LtuNatIdModel ltuNatIdModel = new LtuNatIdModel();

        Method method = LithuanianNationalIdValidator.class.getDeclaredMethod("validateControlDigit", String.class, LtuNatIdModel.class);
        method.setAccessible(true);

        ltuNatIdModel = (LtuNatIdModel) method.invoke(validator, id, ltuNatIdModel);

        assertTrue(ltuNatIdModel.isValid());
    }

    @Test
    public void validateControlDigit_IDWithIncorrectLastDigit_False() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String id = getRandomInvalidId();
        LtuNatIdModel ltuNatIdModel = new LtuNatIdModel();

        Method method = LithuanianNationalIdValidator.class.getDeclaredMethod("validateControlDigit", String.class, LtuNatIdModel.class);
        method.setAccessible(true);

        ltuNatIdModel = (LtuNatIdModel) method.invoke(validator, id, ltuNatIdModel);

        assertFalse(ltuNatIdModel.isValid());
    }

    @Test
    public void calculateControlDigitSum_GivenValidId_ShouldBeEqualsTo283() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        long id = 456_789_456_78L;
        int multiplier = 1;

        Method method = LithuanianNationalIdValidator.class.getDeclaredMethod("calculateControlDigitSum", long.class, int.class);
        method.setAccessible(true);

        validator = new LithuanianNationalIdValidator();
        int sum = (int) method.invoke(validator, id, multiplier);

        assertEquals(283, sum);
    }
}
