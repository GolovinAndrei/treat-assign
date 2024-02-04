package co.il.treat.treatassign.repo;

import co.il.treat.treatassign.model.EstimatedDog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DogRepository extends JpaRepository<EstimatedDog, Long> {

}
