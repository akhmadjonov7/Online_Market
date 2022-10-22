package uz.pdp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.entities.Role;
import uz.pdp.util.RoleEnum;

public interface RoleRepo extends JpaRepository<Role, Integer> {
    Role findByName(RoleEnum name);

}