package ar.edu.utn.frba.dds;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ar.edu.utn.frba.dds.errors.ValidationError;
import ar.edu.utn.frba.dds.validator.HasBothCases;
import ar.edu.utn.frba.dds.validator.HasMinimumLength;
import ar.edu.utn.frba.dds.validator.HasNoWhiteSpaces;
import ar.edu.utn.frba.dds.validator.HasNumbers;
import ar.edu.utn.frba.dds.validator.HasSpecialCharacter;
import ar.edu.utn.frba.dds.validator.PasswordValidator;
import ar.edu.utn.frba.dds.validator.WorstPasswords;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class ErrorFormater {
  List<String> errors = new ArrayList<>();

  public void AddBothCasesError() {
    errors.add(new HasBothCases().error);
  }
  public void AddNoWhiteSpaceError() {
    errors.add(new HasNoWhiteSpaces().error);
  }
  public void AddMinimunLengthError() {
    errors.add(new HasMinimumLength().error);
  }
  public void AddNumbersError() {
    errors.add(new HasNumbers().error);
  }

  public void AddSpecialCharacterError() {
    errors.add(new HasSpecialCharacter().error);
  }

  public void AddWorstPasswordsError() {
    errors.add(new WorstPasswords().error);
  }

  public Boolean CheckError(String error) {
     return errors.stream().allMatch(n -> error.contains(n));
  }
}
public class ValidatorTest {
    private PasswordValidator passwordValidator = new PasswordValidator();

    @Test
    public void passwordIsInvalidWithAllErrors() {
      // Arrange
      String password = "dragon";
      ErrorFormater errorFormater = new ErrorFormater();
      errorFormater.AddMinimunLengthError();
      errorFormater.AddWorstPasswordsError();
      errorFormater.AddNumbersError();
      errorFormater.AddSpecialCharacterError();
      errorFormater.AddBothCasesError();
      // Act
      Exception exception = assertThrows(ValidationError.class, () -> passwordValidator.Validate(password));
      // Assert
      assertTrue(errorFormater.CheckError(exception.getMessage()));
    }

  @Test
  public void passwordWithoutMinimumAndWorstPasswordsLengthError() {
    // Arrange
    String password = "dragones";
    ErrorFormater errorFormater = new ErrorFormater();
    errorFormater.AddNumbersError();
    errorFormater.AddSpecialCharacterError();
    errorFormater.AddBothCasesError();
    // Act
    Exception exception = assertThrows(ValidationError.class, () -> passwordValidator.Validate(password));
    // Assert
    assertTrue(errorFormater.CheckError(exception.getMessage()));
  }

  @Test
  public void passwordWithoutNumberError() {
    // Arrange
    String password = "dragones1";
    ErrorFormater errorFormater = new ErrorFormater();
    errorFormater.AddSpecialCharacterError();
    errorFormater.AddBothCasesError();
    // Act
    Exception exception = assertThrows(ValidationError.class, () -> passwordValidator.Validate(password));
    // Assert
    assertTrue(errorFormater.CheckError(exception.getMessage()));
  }

  @Test
  public void passwordWithoutBothCasesError() {
    // Arrange
    String password = "Dragones1";
    ErrorFormater errorFormater = new ErrorFormater();
    errorFormater.AddSpecialCharacterError();
    // Act
    Exception exception = assertThrows(ValidationError.class, () -> passwordValidator.Validate(password));
    // Assert
    assertTrue(errorFormater.CheckError(exception.getMessage()));
  }

  @Test
  public void passwordWithoutSpecialCharacterError() {
    // Arrange
    String password = "dragones_1";
    ErrorFormater errorFormater = new ErrorFormater();
    errorFormater.AddBothCasesError();
    // Act
    Exception exception = assertThrows(ValidationError.class, () -> passwordValidator.Validate(password));
    // Assert
    assertTrue(errorFormater.CheckError(exception.getMessage()));
  }

  @Test
  public void passwordWithoutErrorsError() {
    // Arrange
    String password = "Dragones_1";
    // Act
    String validation = passwordValidator.Validate(password);
    // Assert
    assertEquals(password, validation);
  }
}
