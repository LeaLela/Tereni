package ba.sum.fsre.tereni.repositories;
import ba.sum.fsre.tereni.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
