package api.crm.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import api.crm.backend.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
