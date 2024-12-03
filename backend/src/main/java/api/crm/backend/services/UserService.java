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
import api.crm.backend.dto.clients.EditClientResponse;
import api.crm.backend.dto.clients.NewClientsResponse;
import api.crm.backend.dto.profile.ChangePasswordRequest;
import api.crm.backend.models.User;
import api.crm.backend.repository.UserRepository;
import api.crm.backend.utils.errors.ConflictException;
import api.crm.backend.utils.errors.CredentialsException;
import api.crm.backend.utils.errors.ForbiddenException;
import api.crm.backend.utils.errors.NotFoundException;

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
                throw new ConflictException("El nombre de usuario ya existe");
            }

            if (userRepository.existsByEmail(registerRequest.getEmail())) {
                throw new CredentialsException("El email ya existe. Inténtelo de nuevo");
            }

            if (!registerRequest.getPassword().equals(registerRequest.getRepeatPassword())) {
                throw new CredentialsException("Las contraseñas deben coincidir");
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
                    .orElseThrow(() -> new NotFoundException("Usuario no encontrado. Inténtelo de nuevo"));

            if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                throw new CredentialsException("Credenciales incorrectas. Inténtelo de nuevo");
            }

            if ("user".equals(user.getRol()) && !user.getActive()) {
                throw new ConflictException("El usuario no está activado");
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

        User userRequest = userRepository.findByEmail(emailFromToken)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado. Inténtelo de nuevo"));

        if (!userRequest.getToken().equals(token)) {
            throw new SecurityException("El token enviado no coincide con el almacenado para el usuario");
        }

        return List.of(new UserResponseDTO(userRequest));
    }

    public User changePassword(String authorizationHeader, ChangePasswordRequest changePasswordRequest) {
        String token = authorizationHeader.substring(7);
        String emailToken = jwtUtils.getEmailFromToken(token);

        User userRequest = userRepository.findByEmail(emailToken)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado. Inténtelo de nuevo"));

        if (!userRequest.getToken().equals(token)) {
            throw new SecurityException("El token enviado no coincide con el almacenado para el usuario");
        }

        if (!passwordEncoder.matches(changePasswordRequest.getPassword(), userRequest.getPassword())) {
            throw new CredentialsException("Credenciales incorrectas. Inténtelo de nuevo");
        }

        if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmPassword())) {
            throw new CredentialsException("La nueva contraseña y la confirmación han de ser iguales");
        }

        userRequest.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        return userRepository.save(userRequest);
    }

    public User createClient(String authorizationHeader, NewClientsResponse newClientsResponse) {
        String token = authorizationHeader.substring(7);
        String emailToken = jwtUtils.getEmailFromToken(token);

        User userRequest = userRepository.findByEmail(emailToken)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado. Inténtelo de nuevo"));

        if (!userRequest.getToken().equals(token)) {
            throw new SecurityException("El token enviado no coincide con el almacenado para el usuario");
        }

        if (!"admin".equals(userRequest.getRol())) {
            throw new ForbiddenException("Usuario no autorizado. Solo los ADMIN pueden realizar esta acción");
        }

        if (userRepository.existsByEmail(newClientsResponse.getEmail())) {
            throw new ConflictException("El cliente ya existe. Vuelva a intentarlo");
        }

        User newClient = new User();

        newClient.setUsername(newClientsResponse.getName());
        newClient.setEmail(newClientsResponse.getEmail());
        newClient.setCompany(newClientsResponse.getCompany());
        newClient.setPhone(newClientsResponse.getPhone());

        newClient.setRol("client");
        newClient.setPassword(passwordEncoder.encode("123123123"));
        newClient.setActive(false);

        return userRepository.save(newClient);
    }

    public List<UserResponseDTO> getClients(String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        String emailFromToken = jwtUtils.getEmailFromToken(token);

        User userRequest = userRepository.findByEmail(emailFromToken)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado. Inténtelo de nuevo"));

        if (!userRequest.getToken().equals(token)) {
            throw new SecurityException("El token enviado no coincide con el almacenado para el usuario");
        }

        List<User> clients = userRepository.findByRol("client");

        return clients.stream()
                .map(UserResponseDTO::new)
                .collect(Collectors.toList());
    }

    public UserResponseDTO getClientById(String authorizationHeader, Long clientId) {
        String token = authorizationHeader.substring(7);
        String emailFromToken = jwtUtils.getEmailFromToken(token);

        User userRequest = userRepository.findByEmail(emailFromToken)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado. Inténtelo de nuevo"));

        if (!userRequest.getToken().equals(token)) {
            throw new SecurityException("El token enviado no coincide con el almacenado para el usuario");
        }

        if (!"admin".equals(userRequest.getRol())) {
            throw new ForbiddenException("Usuario no autorizado. Solo los ADMIN pueden realizar esta acción");
        }

        User client = userRepository.findById(clientId)
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado. Vuelva a intentarlo"));

        return new UserResponseDTO(client);
    }

    public User editClientById(String authorizationHeader, Long clientId, EditClientResponse editClientResponse) {
        String token = authorizationHeader.substring(7);
        String emailToken = jwtUtils.getEmailFromToken(token);

        User userRequest = userRepository.findByEmail(emailToken)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado. Inténtelo de nuevo"));

        if (!userRequest.getToken().equals(token)) {
            throw new SecurityException("El token enviado no coincide con el almacenado para el usuario");
        }

        if (!"admin".equals(userRequest.getRol())) {
            throw new ForbiddenException("Usuario no autorizado. Solo los ADMIN pueden realizar esta acción");
        }

        User userToEdit = userRepository.findById(clientId)
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado. Vuelva a intentarlo"));

        userToEdit.setUsername(editClientResponse.getName());
        userToEdit.setEmail(editClientResponse.getEmail());
        userToEdit.setCompany(editClientResponse.getCompany());
        userToEdit.setPhone(editClientResponse.getPhone());

        return userRepository.save(userToEdit);
    }

    public void deleteClientById(String authorizationHeader, Long clientId) {
        String token = authorizationHeader.substring(7);
        String emailToken = jwtUtils.getEmailFromToken(token);

        User userRequest = userRepository.findByEmail(emailToken)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado. Inténtelo de nuevo"));

        if (!userRequest.getToken().equals(token)) {
            throw new SecurityException("El token enviado no coincide con el almacenado para el usuario");
        }

        if (!"admin".equals(userRequest.getRol())) {
            throw new ForbiddenException("Usuario no autorizado. Solo los ADMIN pueden realizar esta acción");
        }

        User userToDelete = userRepository.findById(clientId)
                .orElseThrow(
                        () -> new NotFoundException("Cliente no encontrado. Vuelva a intentarlo"));

        userRepository.deleteById(userToDelete.getUserId());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'loadUserByUsername'");
    }

}
