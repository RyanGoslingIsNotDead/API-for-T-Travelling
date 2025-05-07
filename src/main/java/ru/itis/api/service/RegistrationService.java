package ru.itis.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.api.dto.MessageDto;
import ru.itis.api.dto.RegistrationForm;
import ru.itis.api.entity.User;
import ru.itis.api.exception.PasswordDoNotMatchException;
import ru.itis.api.exception.UserAlreadyExistException;
import ru.itis.api.repository.UserRepository;
import ru.itis.api.util.JsonUtil;


@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public String saveUser(RegistrationForm dto) {

        if (userRepository.existsByPhoneNumber(dto.getPhoneNumber())) {
            throw new UserAlreadyExistException("Phone number already exist");
        }

        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new PasswordDoNotMatchException("Password do not match");
        }


        User user = new User()
                .setPhoneNumber(dto.getPhoneNumber())
                .setPassword(passwordEncoder.encode(dto.getPassword()));

        userRepository.save(user);

        return "Registration successful";

    }

}