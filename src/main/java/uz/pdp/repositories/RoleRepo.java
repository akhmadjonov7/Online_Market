package uz.pdp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.entities.Role;
import uz.pdp.projections.RoleProjection;
import uz.pdp.util.RoleEnum;

public interface RoleRepo extends JpaRepository<Role, Integer> {
    Role findByName(RoleEnum name);

    @Query(value = "select id, name from roles where name = :name")
    RoleProjection getByName(String name);
}