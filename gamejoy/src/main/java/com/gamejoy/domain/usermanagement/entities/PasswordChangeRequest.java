package com.gamejoy.domain.usermanagement.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordChangeRequest {

  private String oldPassword;
  private String newPassword;
}

/**
 * Request should look like
 * {
 *     "oldPassword": "currentPassword123",
 *     "newPassword": "newSecurePassword123"
 * }
 */