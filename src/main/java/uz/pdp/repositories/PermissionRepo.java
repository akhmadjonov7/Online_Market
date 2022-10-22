package uz.pdp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.entities.Permission;
import uz.pdp.util.PermissionEnum;

import java.util.Optional;

public interface PermissionRepo extends JpaRepository<Permission, Integer> {
    Permission findByName(PermissionEnum name);
}
