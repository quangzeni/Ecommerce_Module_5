package ra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.model.Roles;
import ra.model.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
    //    Page<User> findByEmailOrStatus(String userEmail, Pageable pageable);
    Optional<User> findByUserNameAndStatus(String userName, boolean status);
    List<User> searchByUserNameIsContainingIgnoreCase(String userName);
    Optional<User> findById(Long id);
    User getUserById(Long id);
}
