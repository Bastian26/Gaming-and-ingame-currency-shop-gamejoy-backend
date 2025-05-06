package com.gamejoy.domain.admin.controller;

import com.gamejoy.domain.usermanagement.services.UserService;
import jakarta.validation.Valid;
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


 /* //todo: still todo
  @PostMapping("/changePassword")
  public ResponseEntity<String> changePassword(Long id, @Valid char[] password) {
    String passwordChangeResponse = userService.changePassword(id, password);
    return ResponseEntity.ok().body(passwordChangeResponse);
  }*/

  /*@POST
  @Path("user/create")
  @Produces(MediaType.APPLICATION_JSON)
  @Secured("ROLE_ADMIN")
  public User createUser(User user) {
    if(user.getPassword() == null || user.getPassword().equals("")) {
      //throw exception...
    }
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    userService.save(user);
    return user;
}


}
 @PutMapping("/users/{username}/reset-password")
@PreAuthorize("hasRole('ADMIN')")
public ResponseEntity<?> resetPassword(@PathVariable String username,
                                       @RequestBody String newPassword) {
    userService.resetPasswordAsAdmin(username, newPassword);
    return ResponseEntity.ok("Password reset by admin.");
}

   */


/*
Benutzerverwaltung:

Alle Benutzer anzeigen / suchen

Benutzer deaktivieren / aktivieren

Rollen zuweisen / ändern

System-Monitoring / Metriken:

Systemstatus

Log-Einträge / Audit-Trails

API-Nutzungsstatistiken

Verwaltung von Ressourcen:

Inhalte erstellen / ändern / löschen (z. B. Beiträge, Produkte, Einstellungen)

Berechtigungen steuern

Admin-spezifische Aufgaben:

Manuelle Datenpflege

Export/Import von Daten

Konfiguration von globalen Einstellungen
 */

}