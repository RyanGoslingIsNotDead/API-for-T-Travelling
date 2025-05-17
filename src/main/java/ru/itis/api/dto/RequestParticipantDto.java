package ru.itis.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RequestParticipantDto {
    @Pattern(regexp = "^8\\d{10}$", message = "The number must start with 8 and contain 11 digits.")
    @NotBlank(message = "The number should not contain spaces")
    @NotNull(message = "The number must not be empty")
    @Schema(description = "Phone number of the user. Must start with 8 and have 11 digits.", example = "89876543210")
    private String phoneNumber;
}
