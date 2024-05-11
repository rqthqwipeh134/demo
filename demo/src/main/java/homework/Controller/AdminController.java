package homework.Controller;

import homework.DO.AuthenticationHeader;
import homework.DO.UserAccessDTO;
import homework.Service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AuthenticationService authService;

    private final static String ACCESS_FILE = "access.json";

    @PostMapping("/addUser")
    public ResponseEntity<?> addUser(@RequestBody UserAccessDTO userAccess, @RequestHeader("Authorization") String header) throws AuthenticationService.AuthenticationException {

        AuthenticationHeader authHeader = authService.parseAndValidateHeader(header);
        Objects.requireNonNull(authHeader.getRole(), "User Role cannot be null");

        if (!"admin".equals(authHeader.getRole())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only admins can add users.");
        }

        Long userId = userAccess.getUserId();
        String[] endpoints = userAccess.getEndpoints().toArray(new String[0]);

        try {
            Files.write(Paths.get(ACCESS_FILE), (userId + ": " + String.join(",", endpoints) + "\n").getBytes());
            return ResponseEntity.ok("Access granted successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while granting access: " + e.getMessage());
        }
    }
}




















