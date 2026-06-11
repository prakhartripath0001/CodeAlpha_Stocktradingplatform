package com.stocktrading.controller;

import com.stocktrading.model.User;
import com.stocktrading.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * REST controller for user registration and retrieval.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Register a new user.
     * POST /api/users
     * Body: { "username": "...", "email": "..." }
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> createUser(@Valid @RequestBody User user) {
        User created = userService.createUser(user);
        Map<String, Object> response = buildUserMap(created);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Get a user by username.
     * GET /api/users/{username}
     */
    @GetMapping("/{username}")
    public ResponseEntity<Map<String, Object>> getUser(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        return ResponseEntity.ok(buildUserMap(user));
    }

    /**
     * List all registered users.
     * GET /api/users
     */
    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAllUsers() {
        List<Map<String, Object>> users = userService.getAllUsers().stream()
                .map(this::buildUserMap)
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    /**
     * Build a serializable map from a User entity,
     * avoiding lazy-loading issues with collections.
     */
    private Map<String, Object> buildUserMap(User user) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", user.getId());
        map.put("username", user.getUsername());
        map.put("email", user.getEmail());
        map.put("balance", user.getBalance());
        map.put("createdAt", user.getCreatedAt());
        return map;
    }
}
