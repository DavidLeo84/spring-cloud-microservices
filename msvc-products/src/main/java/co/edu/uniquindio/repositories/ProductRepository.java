package co.edu.uniquindio.repositories;

import co.edu.uniquindio.libs.commons.msvc.libs.commons.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
