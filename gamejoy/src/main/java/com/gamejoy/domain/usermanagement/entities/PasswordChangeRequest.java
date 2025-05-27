package com.gamejoy.domain.usermanagement.entities;

import com.gamejoy.config.validation.customannotation.ValidPassword;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordChangeRequest {

  @ValidPassword
  private char[] oldPassword;
  private char[] newPassword;
}

/**
 * Request should look like
 * {
 *     "oldPassword": "currentPassword123",
 *     "newPassword": "newSecurePassword123"
 * }
 */