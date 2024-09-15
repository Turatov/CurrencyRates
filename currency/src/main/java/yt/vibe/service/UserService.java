package yt.vibe.service;

import org.assertj.core.internal.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import yt.vibe.dto.UserDTO;
import yt.vibe.entities.Authority;
import yt.vibe.entities.User;
import yt.vibe.repository.AuthorRepository;
import yt.vibe.repository.UserRepository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    public String getCurrentUserRoles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(", "));
        }
        return "No roles or not authenticated";
    }


    public void createNewUserWithRoles(UserDTO userDTO) {
        if (userRepository.existsById(userDTO.getUsername())) {
            throw new IllegalArgumentException(String.format(" User %s already exists", userDTO.getUsername()));
        }

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setEnabled(true);
        Authority authority = new Authority();
        authority.setUser(user);
        if (Objects.equals(getCurrentUserRoles(), "ROLE_ADMIN") || userDTO.getReferenceCode() != null) {
            authority.setAuthority("ROLE_" + userDTO.getRole().toUpperCase());
        } else
            authority.setAuthority("ROLE_USER");
        user.setAuthorities(List.of(authority));
        userRepository.save(user);

    }

    public User getUserByUserName(String userName) {
        return userRepository.findById(userName).orElse(null);
    }

}
