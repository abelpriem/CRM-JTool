package api.crm.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import api.crm.backend.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
