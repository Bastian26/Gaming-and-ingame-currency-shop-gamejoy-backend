package com.gamejoy.domain.usermanagement.controllers;

import com.gamejoy.config.security.model.UserPrincipal;
import com.gamejoy.domain.common.dto.api.ApiError;
import com.gamejoy.domain.common.dto.api.ApiResponseWrapper;
import com.gamejoy.domain.usermanagement.dto.UserDto;
import com.gamejoy.domain.usermanagement.entities.PasswordChangeRequest;
import com.gamejoy.domain.usermanagement.mappers.UserMapper;
import com.gamejoy.domain.usermanagement.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "User Management", description = "Operations for managing users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/{id}")
    @Operation(summary = "Get user by id", description = "Retrieves a single user by id.")
    @ApiResponses({
      @ApiResponse(responseCode = "200", description = "User found", content = @Content(schema = @Schema(implementation = UserDto.class))),
      @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<ApiResponseWrapper<UserDto>> getUserById(@PathVariable Long id) {
        UserDto userDto = userService.getUserById(id);

        ApiResponseWrapper<UserDto> response = new ApiResponseWrapper<>(
          userDto, String.format("User with id %d retrieved", id)
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping()
    @Operation(summary = "List all users", description = "Retrieves all users.")
    @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Users retrieved", content = @Content(schema = @Schema(implementation = UserDto.class)))
    })
    public ResponseEntity<ApiResponseWrapper<List<UserDto>>> getAllUsers() {
        List<UserDto> userDtoList = userService.getAllUsers();

        ApiResponseWrapper<List<UserDto>> response = new ApiResponseWrapper<>(
          userDtoList, "Retrieved all Users"
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user", description = "Deletes a user by id.")
    @ApiResponses({
      @ApiResponse(responseCode = "204", description = "User deleted"),
      @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();  // Return HTTP 204 No Content to indicate successful deletion
    }
/*
    @GetMapping
    public ResponseEntity<UserDto> updateUser(Long id) {
        UserDto userDto = userService.getUserById(id);



        return ResponseEntity.ok().body(userDto);
    }*/

    /*//todo: still todo - move to admin
    @PostMapping("/changeUsername")
    public ResponseEntity<String> changeUsername(Long id, @Valid String username) {
        String usernameChangeResponse = userService.changeUsername(id, username);
        return ResponseEntity.ok().body(usernameChangeResponse);
    }**/

    // User should only change pw for himself
    @PostMapping("/change-password")
    @Operation(summary = "Change own password", description = "Authenticated user changes their password.")
    @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Password changed"),
      @ApiResponse(responseCode = "400", description = "Validation failed", content = @Content(schema = @Schema(implementation = ApiError.class))),
      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    public ResponseEntity<String> changePassword(@RequestBody @Valid PasswordChangeRequest request,
      @AuthenticationPrincipal UserPrincipal userDetails) {

        String response = userService.changePassword(
          userDetails.user().getId(), // username of authenticated user (fetched from SecurityContext by @AuthenticationPrincipal)
          request
        );

        return ResponseEntity.ok(response);
    }
}
