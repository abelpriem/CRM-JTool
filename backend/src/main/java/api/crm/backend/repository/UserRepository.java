package api.crm.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import api.crm.backend.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @SuppressWarnings("null")
    Optional<User> findById(Long id);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}
