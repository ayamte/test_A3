package com.wasalny.auth.controller;

import com.wasalny.auth.entity.User;
import com.wasalny.auth.dto.UserDto;
import com.wasalny.auth.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/users")
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        UserDto userDto = new UserDto();
        userDto.setId(currentUser.getId());
        userDto.setUsername(currentUser.getUsername());
        userDto.setEmail(currentUser.getEmail());
        userDto.setEnabled(currentUser.isEnabled());

        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/")
    public ResponseEntity<List<User>> allUsers() {
        List<User> users = userService.allUsers();
        return ResponseEntity.ok(users);
    }
}