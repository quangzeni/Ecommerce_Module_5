package ra.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.model.Roles;
import ra.repository.RoleRepository;
import ra.service.RoleService;

import java.util.List;
@Service
public class RoleServiceImp implements RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public List<Roles> findAll() {
        List<Roles> rolesList = roleRepository.findAll();
        return rolesList;
    }
}
