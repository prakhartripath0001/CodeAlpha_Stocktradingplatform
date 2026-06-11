package com.stocktrading.service;

import com.stocktrading.exception.ResourceNotFoundException;
import com.stocktrading.model.User;
import com.stocktrading.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service handling user registration and retrieval.
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Register a new user with default starting balance.
     *
     * @param user the user to create (username and email required)
     * @return the persisted user
     * @throws IllegalArgumentException if username or email already exists
     */
    @Transactional
    public User createUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Username '" + user.getUsername() + "' is already taken");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email '" + user.getEmail() + "' is already registered");
        }
        return userRepository.save(user);
    }

    /**
     * Find a user by username.
     *
     * @param username the username to search for
     * @return the found user
     * @throws ResourceNotFoundException if no user exists with the given username
     */
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));
    }

    /**
     * Retrieve all registered users.
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
