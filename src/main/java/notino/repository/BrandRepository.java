package notino.repository;

import notino.domain.entities.Brand;
import notino.domain.models.service.BrandServiceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<Brand, String> {

    Optional<Brand> findByName(String name);
}
