package norvina.repository;

import norvina.domain.entities.DailyOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DailyOfferRepository extends JpaRepository<DailyOffer, String> {

    Optional<DailyOffer> findByProduct_Id(String id);

}
