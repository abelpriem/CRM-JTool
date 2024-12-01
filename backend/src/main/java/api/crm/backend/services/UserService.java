package api.crm.backend.services;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import api.crm.backend.config.jwt.JwtUtils;
import api.crm.backend.dto.UserResponseDTO;
import api.crm.backend.dto.auth.LoginRequest;
import api.crm.backend.dto.auth.RegisterRequest;
import api.crm.backend.dto.profile.ChangePasswordRequest;
import api.crm.backend.models.User;
import api.crm.backend.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    public User create(RegisterRequest registerRequest) {
        try {
            if (userRepository.existsByUsername(registerRequest.getUsername())) {
                throw new IllegalArgumentException("El nombre de usuario ya existe");
            }

            if (userRepository.existsByEmail(registerRequest.getEmail())) {
                throw new IllegalArgumentException("El email ya existe. Inténtelo de nuevo");
            }

            if (!registerRequest.getPassword().equals(registerRequest.getRepeatPassword())) {
                throw new IllegalArgumentException("Las contraseñas deben coincidir");
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
            System.out.println(error.getMessage());
            throw error;
        }
    }

    public Map<String, String> login(LoginRequest loginRequest) {
        try {
            User user = userRepository.findByEmail(loginRequest.getEmail())
                    .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado. Inténtelo de nuevo"));

            if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                throw new IllegalArgumentException("Credenciales incorrectas. Inténtelo de nuevo");
            }

            if ("user".equals(user.getRol()) && !user.getActive()) {
                throw new IllegalArgumentException("El usuario no está activado");
            }

            String token = jwtUtils.generateToken(user.getEmail());

            user.setToken(token);
            userRepository.save(user);

            return Map.of("username", user.getUsername(), "token", user.getToken(), "rol", user.getRol());
        } catch (IllegalArgumentException error) {
            System.out.println(error.getMessage());
            throw error;
        }
    }

    public List<UserResponseDTO> getUser(String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        String emailFromToken = jwtUtils.getEmailFromToken(token);

        User user = userRepository.findByEmail(emailFromToken)
                .orElseThrow(() -> new IllegalArgumentException("Usuario con el correo del token no encontrado"));

        if (!user.getToken().equals(token)) {
            throw new SecurityException("El token enviado no coincide con el almacenado para el usuario");
        }

        return List.of(new UserResponseDTO(user));
    }

    public List<UserResponseDTO> getClients(String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        String emailFromToken = jwtUtils.getEmailFromToken(token);

        User user = userRepository.findByEmail(emailFromToken)
                .orElseThrow(() -> new IllegalArgumentException("Usuario con el correo del token no encontrado"));

        if (!user.getToken().equals(token)) {
            throw new SecurityException("El token enviado no coincide con el almacenado para el usuario");
        }

        List<User> clients = userRepository.findByRol("client");

        return clients.stream()
                .map(UserResponseDTO::new)
                .collect(Collectors.toList());
    }

    public User changePassword(String authorizationHeader, ChangePasswordRequest changePasswordRequest) {
        String token = authorizationHeader.substring(7);
        String emailToken = jwtUtils.getEmailFromToken(token);

        User user = userRepository.findByEmail(emailToken)
                .orElseThrow(() -> new IllegalArgumentException("Usuario con el correo del token no encontrado"));

        if (!passwordEncoder.matches(changePasswordRequest.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Credenciales incorrectas. Inténtelo de nuevo");
        }

        if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmPassword())) {
            throw new IllegalArgumentException("La nueva contraseña y la confirmación han de ser iguales");
        }

        user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'loadUserByUsername'");
    }

}
