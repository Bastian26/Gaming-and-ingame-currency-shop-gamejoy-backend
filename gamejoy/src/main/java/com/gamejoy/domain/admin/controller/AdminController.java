package com.gamejoy.domain.admin.controller;

import com.gamejoy.domain.usermanagement.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// todo: only concept right now
@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

//  private final UserService userService;
//
//  @PostMapping("/users/changeUsername")
//  public ResponseEntity<String> changeUsername(@RequestParam Long id, @RequestParam String username) {
//    return ResponseEntity.ok(userService.changeUsername(id, username));
//  }
//
//  @PostMapping("/users/changePassword")
//  public ResponseEntity<String> changePassword(@RequestParam Long id, @RequestParam String password) {
//    return ResponseEntity.ok(userService.changePassword(id, password.toCharArray()));
//  }
}
