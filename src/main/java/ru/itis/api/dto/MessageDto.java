package ru.itis.api.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

@Data
@Accessors(chain = true)
public class MessageDto {

    private Boolean statusSuccess;

    private String message;

}
