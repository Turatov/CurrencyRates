package yt.vibe.contoller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import yt.vibe.dto.UserDTO;
import yt.vibe.entities.Authority;
import yt.vibe.entities.User;
import yt.vibe.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    @Autowired
    UserService userService;

    @Operation(summary = "Creat new user with role", description = "if in request reference code exist role by default will be ADMIN if not USER , after authentication can change it")
    @PostMapping()
    private ResponseEntity<String> creatNewUser(@RequestBody UserDTO user) {
        try {
            userService.createNewUserWithRoles(user);
            return ResponseEntity.created(ServletUriComponentsBuilder.
                            fromCurrentRequest().path("/api/auth/users/{userName}").
                            buildAndExpand(user.getUsername()).
                            toUri()).
                    body("User created successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @Operation(summary = "Get user role by username", description = "returns user role and id if exist")
    @GetMapping("/users/{userName}")
    private List<Authority> getUser(@PathVariable String userName) {
        User user = userService.getUserByUserName(userName);
        return user.getAuthorities();
    }
}
