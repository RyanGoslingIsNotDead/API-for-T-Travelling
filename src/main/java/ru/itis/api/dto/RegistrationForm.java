package ru.itis.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RegistrationForm {

    @Pattern(regexp = "^8\\d{10}$", message = "The number must start with 8 and contain 11 digits.")
    @NotBlank(message = "The number should not contain spaces")
    @NotNull(message = "The number must not be empty")
    private String phoneNumber;

    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{6,15}$", message = "The password must contain one uppercase letter and one number, and must be between 6 and 15 characters long.")
    @NotBlank(message = "The password should not contain spaces")
    @NotNull(message = "The password must not be empty")
    private String password;

    private String confirmPassword;

}
