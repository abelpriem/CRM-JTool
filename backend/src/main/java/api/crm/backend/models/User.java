package api.crm.backend.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String company;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String rol;

    @Column(nullable = false)
    private Boolean active;

    @Column(nullable = true)
    private String token;

    public User(Long userId, String username, String email, String password, String company, String phone, String rol,
            Boolean active, String token) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.company = company;
        this.phone = phone;
        this.rol = rol;
        this.active = active;
        this.token = token;
    }

    public User() {
    }
}
