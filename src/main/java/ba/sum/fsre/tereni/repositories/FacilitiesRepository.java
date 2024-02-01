package ba.sum.fsre.tereni.repositories;

import ba.sum.fsre.tereni.models.Facilities;
import ba.sum.fsre.tereni.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FacilitiesRepository extends JpaRepository<Facilities, Long> {

}