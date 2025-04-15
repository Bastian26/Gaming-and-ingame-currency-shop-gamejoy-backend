package com.gamejoy.domain.usermanagement.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import com.gamejoy.domain.usermanagement.services.UserService;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //todo: still todo
    @PostMapping("/changeUsername")
    public ResponseEntity<String> changeUsername(Long id, @Valid String username) {
        String usernameChangeResponse = userService.changeUsername(id, username);
        return ResponseEntity.ok().body(usernameChangeResponse);
    }

    //todo: still todo
    @PostMapping("/changePassword")
    public ResponseEntity<String> changePassword(Long id, @Valid char[] password) {
        String passwordChangeResponse = userService.changePassword(id, password);
        return ResponseEntity.ok().body(passwordChangeResponse);
    }


}
