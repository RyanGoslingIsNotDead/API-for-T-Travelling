package ru.itis.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

@Data
@Accessors(chain = true)
@Schema(description = "Standard API response format")
public class MessageDto {

    @Schema(description = "Indicates whether the operation was successful")
    private Boolean statusSuccess;

    @Schema(description = "Message describing the result of the operation")
    private String message;

}
