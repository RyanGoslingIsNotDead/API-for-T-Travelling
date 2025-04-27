package ru.itis.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.api.dto.MessageDto;
import ru.itis.api.dto.RegistrationForm;
import ru.itis.api.entity.User;
import ru.itis.api.repository.UserRepository;
import ru.itis.api.util.JsonUtil;


@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<String> saveUser(RegistrationForm dto) {

        MessageDto messageDto = new MessageDto();

        if (userRepository.existsByPhoneNumber(dto.getPhoneNumber())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    JsonUtil.write(messageDto
                            .setStatusSuccess(false)
                            .setMessage("Phone is already exist"))
            );
        }

        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    JsonUtil.write(messageDto
                            .setStatusSuccess(false)
                            .setMessage("Password do not match"))
            );
        }


        User user = new User()
                .setPhoneNumber(dto.getPhoneNumber())
                .setPassword(passwordEncoder.encode(dto.getPassword()));

        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                JsonUtil.write(messageDto
                        .setStatusSuccess(true)
                        .setMessage("Registration successful"))
        );

    }

}