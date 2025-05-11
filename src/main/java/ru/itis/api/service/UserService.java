package ru.itis.api.service;


import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.api.dto.RegistrationForm;
import ru.itis.api.dto.UserDto;
import ru.itis.api.dto.UserWithPasswordDto;
import ru.itis.api.entity.User;
import ru.itis.api.exception.PasswordDoNotMatchException;
import ru.itis.api.exception.UserAlreadyExistException;
import ru.itis.api.exception.UserNotFoundException;
import ru.itis.api.mapper.UserMapper;
import ru.itis.api.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserDto getUserByPhoneNumber(String phoneNumber) {
        User user = userRepository.findByPhoneNumber(phoneNumber).
                orElseThrow(() -> new UsernameNotFoundException("User not found with phone number: " + phoneNumber));

        return userMapper.mapToProfileDto(user);

    }

    public String updateUser(UserWithPasswordDto userWithPasswordDto, String phoneNumber) {

        User user = userRepository.findByPhoneNumber(phoneNumber).
                orElseThrow(() -> new UserNotFoundException("User not found with phone number: " + phoneNumber));

        if (!userWithPasswordDto.getPassword().isEmpty()) {

            if (!userWithPasswordDto.getPassword().equals(userWithPasswordDto.getConfirmPassword())) {
                throw new PasswordDoNotMatchException("Password do not match");
            }

            user.setPassword(passwordEncoder.encode(userWithPasswordDto.getPassword()));
        }

        if (!userWithPasswordDto.getPhoneNumber().equals(phoneNumber)) {

            if (userRepository.existsByPhoneNumber(userWithPasswordDto.getPhoneNumber())){
                throw new UserAlreadyExistException("Phone number already exist");
            }

            user.setPhoneNumber(userWithPasswordDto.getPhoneNumber());
        }

        if (!userWithPasswordDto.getFirstName().equals(user.getFirstName())) {
            user.setFirstName(userWithPasswordDto.getFirstName());
        }

        if (!userWithPasswordDto.getLastName().equals(user.getLastName())) {
            user.setLastName(userWithPasswordDto.getLastName());
        }

        userRepository.save(user);

        return "User successfully updated";

    }
}
