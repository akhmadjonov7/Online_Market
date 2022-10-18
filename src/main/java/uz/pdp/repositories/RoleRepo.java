package uz.pdp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.entities.Role;

public interface RoleRepo extends JpaRepository<Role, Integer> {
    Role findByName(String name);
}