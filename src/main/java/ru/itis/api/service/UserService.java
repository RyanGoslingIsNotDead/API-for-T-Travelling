package ru.itis.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.itis.api.dto.UserDto;
import ru.itis.api.entity.User;
import ru.itis.api.mapper.UserMapper;
import ru.itis.api.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<User> getAllByPhoneNumbers(List<String> phoneNumbers) {
        return userRepository.findAllByPhoneNumbers(phoneNumbers);
    }

    public User getUserByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber).
                orElseThrow(() -> new UsernameNotFoundException("User not found with phone number: " + phoneNumber));
    }
}