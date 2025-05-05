package ru.itis.api.web.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.api.dto.MessageDto;
import ru.itis.api.dto.RegistrationForm;
import ru.itis.api.service.RegistrationService;

@RestController
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints for user registration and authentication")
@RequestMapping("/api/v1/registration")
public class RegistrationRestController {

    private final RegistrationService registrationService;

    @PostMapping
    @Operation(
            summary = "Register a new user",
            description = "Creates a new user in the system",
            responses = {
                    @ApiResponse(responseCode = "201", description = "User registered successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = MessageDto.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input data",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = MessageDto.class))),
            }
    )
    public ResponseEntity<String> registration(@RequestBody RegistrationForm dto) {
        return registrationService.saveUser(dto);
    }

}
