package ar.edu.utn.frba.dds.validator;

import ar.edu.utn.frba.dds.errors.ValidationError;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PasswordValidator {
    private List<Validator> validators = new ArrayList<>(
        Arrays.asList(
            new WorstPasswords(),
            new HasBothCases(),
            new HasMinimumLength(),
            new HasNoWhiteSpaces(),
            new HasNumbers(),
            new HasSpecialCharacter()
        )
    );

    public String Validate(String password) {
        List<String> errors = new ArrayList<>();
        errors=validators.stream().map(validator->validator.validate(password)).filter(Objects::nonNull).collect(Collectors.toList());
        if (errors.size() > 0)
            throw new ValidationError("Los errores fueron: \n - " + String.join("\n - ", errors));
        return password;
    }
}
