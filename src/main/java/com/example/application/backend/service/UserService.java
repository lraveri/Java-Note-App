package com.example.application.backend.service;

import com.example.application.backend.model.entity.User;
import com.example.application.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User findByUsername(String username) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        return userOpt.orElse(null);
    }

    public User findByActivationCode(String activationCode) {
        Optional<User> userOpt = userRepository.findByActivationCode(activationCode);
        return userOpt.orElse(null);
    }


    public User save(User user) {
        return userRepository.save(user);
    }
}
