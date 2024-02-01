package ba.sum.fsre.tereni.repositories;
import ba.sum.fsre.tereni.models.Reservation;
import ba.sum.fsre.tereni.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query("SELECT r FROM Reservation r WHERE r.user = :user")
    List<Reservation> findByUser(@Param("user")User user);
}
