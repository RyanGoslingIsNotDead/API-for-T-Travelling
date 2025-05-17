package ru.itis.api.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors
public class PhoneNumberDto {
    private String phoneNumber;
    private String password;
}
