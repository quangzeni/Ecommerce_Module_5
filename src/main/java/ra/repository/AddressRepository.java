package ra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.model.Address;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findAllByUserId(Long id);
}
