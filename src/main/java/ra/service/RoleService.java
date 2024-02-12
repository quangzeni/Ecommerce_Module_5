package ra.service;

import org.springframework.stereotype.Service;
import ra.model.Roles;

import java.util.List;
@Service
public interface RoleService {
    List<Roles> findAll();
}
