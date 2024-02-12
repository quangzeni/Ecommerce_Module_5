package ra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.model.Roles;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Roles,Long> {

}
