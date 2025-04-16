package com.gamejoy.domain.usermanagement.dtos;

import com.gamejoy.domain.usermanagement.entities.Address;
import com.gamejoy.domain.usermanagement.entities.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Represents a user in the system with personal details, address, role, and token.",
  example = "{ id: 1, firstName: 'John', lastName: 'Doe', userName: 'johndoe', email: 'john.doe@example.com', ... }") // Example is not necessary usually
public class UserDto {

    @Schema(description = "Unique identifier of the user", example = "1")
    private Long id;
    @Schema(description = "First name of the user", example = "John")
    private String firstName;
    @Schema(description = "Last name of the user", example = "Doe")
    private String lastName;
    @Schema(description = "Last name of the user", example = "Doe")
    private String userName;
    @Schema(description = "Email address of the user", example = "johndoe@example.com")
    private String email;
    @Schema(description = "Telephone number of the user", example = "+123456789")
    private String telNr;
    @Schema(description = "Address of the user", implementation = Address.class)
    private Address address;
    @Schema(description = "Role of the user in the system", implementation = UserRole.class)
    private UserRole userRole;
    @Schema(description = "JWT token for authentication", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoxMjM0NTY3ODkwLCJleHBpcnkiOiJleGFtcGxlIl0.abc123xyz")
    private String token;
}
