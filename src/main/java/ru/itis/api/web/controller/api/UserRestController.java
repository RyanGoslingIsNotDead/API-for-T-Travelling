package ru.itis.api.web.controller.api;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.itis.api.dto.MessageDto;
import ru.itis.api.dto.RegistrationForm;
import ru.itis.api.dto.UserWithPasswordDto;
import ru.itis.api.exception.PasswordDoNotMatchException;
import ru.itis.api.exception.UserAlreadyExistException;
import ru.itis.api.exception.UserNotFoundException;
import ru.itis.api.service.JwtService;
import ru.itis.api.service.UserService;
import ru.itis.api.util.JsonUtil;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/profile")
@Tag(name = "User Profile API", description = "Operations related to user profile management")
public class UserRestController {

    private final UserService userService;
    private final JwtService jwtService;

    @GetMapping
    @Operation(summary = "Get current user's profile",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved user profile"),
                    @ApiResponse(responseCode = "400", description = "User not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized access - missing or invalid token")
            })
    public ResponseEntity<String> getUser(HttpServletRequest request) {
        String rawToken = jwtService.getRawToken(request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(JsonUtil.write(userService.getUserByPhoneNumber(jwtService.getPhoneNumber(rawToken)))
                );
    }

    @PostMapping
    @Operation(summary = "Update current user's profile",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Profile successfully updated"),
                    @ApiResponse(responseCode = "400", description = "Validation error or password mismatch", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized access - missing or invalid token"),
            })
    public ResponseEntity<String> updateUser(@Valid @RequestBody UserWithPasswordDto userWithPasswordDto, HttpServletRequest request) {
        String rawToken = jwtService.getRawToken(request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(JsonUtil.write(new MessageDto()
                                .setStatusSuccess(true)
                                .setMessage(userService.updateUser(userWithPasswordDto, jwtService.getPhoneNumber(rawToken)))
                        )
                );
    }


    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<String> handleUserAlreadyExistException(UserAlreadyExistException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(JsonUtil.write(new MessageDto()
                        .setStatusSuccess(false)
                        .setMessage(e.getMessage()))
                );
    }

    @ExceptionHandler(PasswordDoNotMatchException.class)
    public ResponseEntity<String> handlePasswordDoNotMatchException(PasswordDoNotMatchException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(JsonUtil.write(new MessageDto()
                        .setStatusSuccess(false)
                        .setMessage(e.getMessage()))
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(JsonUtil.write(new MessageDto()
                        .setStatusSuccess(false)
                        .setMessage(
                                e.getAllErrors()
                                        .get(0)
                                        .getDefaultMessage()
                        ))

                );
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(JsonUtil.write(new MessageDto()
                        .setStatusSuccess(false)
                        .setMessage(e.getMessage()))
                );
    }






}
