package homework.Controller;

import homework.DO.AuthenticationHeader;
import homework.Service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

@RestController
@RequestMapping("/user")
public class UserController {

    private final static String ACCESS_FILE = "access.json";

    @Autowired
    private AuthenticationService authService;

    @GetMapping("/{resource}")
    public ResponseEntity<?> accessResource(@PathVariable String resource, @RequestHeader("Authorization") String authHeader) throws AuthenticationService.AuthenticationException {

        AuthenticationHeader header = authService.parseAndValidateHeader(authHeader);

        try (BufferedReader br = new BufferedReader(new FileReader(ACCESS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(": ");
                if (parts.length == 2 && parts[0].equals(String.valueOf(header.getUserId()))) {
                    String[] grantedEndpoints = parts[1].split(",");
                    if (Arrays.asList(grantedEndpoints).contains(resource)) {
                        return ResponseEntity.ok("You have access to the resource: " + resource);
                    }
                }
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while checking access: " + e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access to the resource is forbidden.");
    }
}














