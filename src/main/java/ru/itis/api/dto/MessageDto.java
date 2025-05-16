package ru.itis.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Schema(description = "Standard API response format")
public class MessageDto {
    @Schema(description = "Message describing the result of the operation")
    private String message;
}
