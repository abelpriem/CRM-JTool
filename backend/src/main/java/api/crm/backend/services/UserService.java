package api.crm.backend.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import api.crm.backend.dto.auth.RegisterRequest;
import api.crm.backend.models.User;
import api.crm.backend.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User create(RegisterRequest registerRequest) {
        try {
            if (userRepository.existsByUsername(registerRequest.getUsername())) {
                throw new IllegalArgumentException("Username already exist. Try again");
            }

            if (userRepository.existsByEmail(registerRequest.getEmail())) {
                throw new IllegalArgumentException("Email already exist. Try again");
            }

            if (!registerRequest.getPassword().equals(registerRequest.getRepeatPassword())) {
                throw new IllegalArgumentException("Password must be the same");
            }

            User user = new User();

            user.setUsername(registerRequest.getUsername());
            user.setEmail(registerRequest.getEmail());
            user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            user.setActive(false);
            user.setRol("user");
            user.setCompany("CRM-Jtool");
            user.setToken(null);

            String phoneNumber = "6" + String.format("%08d", (int) (Math.random() * 1_0000_0000));
            user.setPhone(phoneNumber);

            return userRepository.save(user);
        } catch (IllegalArgumentException error) {
            System.out.println("Error: " + error.getMessage());
            throw error;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'loadUserByUsername'");
    }

}
