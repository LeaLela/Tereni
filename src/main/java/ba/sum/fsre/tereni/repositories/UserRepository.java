package ba.sum.fsre.tereni.repositories;

import ba.sum.fsre.tereni.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
public interface UserRepository extends JpaRepository<User,Long> {
}
