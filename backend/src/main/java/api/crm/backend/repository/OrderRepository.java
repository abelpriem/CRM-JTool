package api.crm.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import api.crm.backend.models.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
