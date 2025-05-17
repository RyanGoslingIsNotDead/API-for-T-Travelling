package ru.itis.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.api.dto.UpdateUserDto;
import ru.itis.api.dto.UserDto;
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
        return userMapper.mapToUserDto(user);
    }

    public UpdateUserDto updateUser(UpdateUserDto updateUserDto, String phoneNumber) {
        User user = userRepository.findByPhoneNumber(phoneNumber).
                orElseThrow(() -> new UserNotFoundException("User not found with phone number: " + phoneNumber));
        if (!updateUserDto.getPassword().isEmpty()) {
            if (!updateUserDto.getPassword().equals(updateUserDto.getConfirmPassword())) {
                throw new PasswordDoNotMatchException("Password do not match");
            }
            user.setPassword(passwordEncoder.encode(updateUserDto.getPassword()));
        }
        if (!updateUserDto.getPhoneNumber().equals(phoneNumber)) {
            if (userRepository.existsByPhoneNumber(updateUserDto.getPhoneNumber())){
                throw new UserAlreadyExistException("Phone number already exist");
            }
            user.setPhoneNumber(updateUserDto.getPhoneNumber());
        }
        if (!updateUserDto.getFirstName().equals(user.getFirstName())) {
            user.setFirstName(updateUserDto.getFirstName());
        }
        if (!updateUserDto.getLastName().equals(user.getLastName())) {
            user.setLastName(updateUserDto.getLastName());
        }
        userRepository.save(user);
        return updateUserDto;
    }
}