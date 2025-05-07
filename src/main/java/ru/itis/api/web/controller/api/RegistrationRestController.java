package ru.itis.api.web.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.itis.api.dto.MessageDto;
import ru.itis.api.dto.RegistrationForm;
import ru.itis.api.exception.PasswordDoNotMatchException;
import ru.itis.api.exception.UserAlreadyExistException;
import ru.itis.api.service.RegistrationService;
import ru.itis.api.util.JsonUtil;

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
    public ResponseEntity<String> registration(
            @Valid
            @RequestBody
            RegistrationForm dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(JsonUtil.write(new MessageDto()
                                .setMessage(registrationService.saveUser(dto))
                )
        );
    }


    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<String> handleUserAlreadyExistException(UserAlreadyExistException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(JsonUtil.write(new MessageDto()
                        .setMessage(e.getMessage()))
        );
    }

    @ExceptionHandler(PasswordDoNotMatchException.class)
    public ResponseEntity<String> handleUserAlreadyExistException(PasswordDoNotMatchException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(JsonUtil.write(new MessageDto()
                        .setMessage(e.getMessage()))
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(JsonUtil.write(new MessageDto()
                        .setMessage(
                                e.getAllErrors()
                                        .get(0)
                                        .getDefaultMessage()
                        ))
                );
    }


}
