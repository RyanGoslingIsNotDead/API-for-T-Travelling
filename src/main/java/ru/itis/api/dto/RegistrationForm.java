package ru.itis.api.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RegistrationForm {

    private String phoneNumber;

    private String password;

    private String confirmPassword;

}
