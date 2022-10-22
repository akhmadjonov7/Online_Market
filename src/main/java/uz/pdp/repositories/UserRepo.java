package uz.pdp.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.projections.RoleProjection;
import uz.pdp.projections.UserProjection;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Integer> {

    @Query(value = "select u.id as id, u.full_name as fullName, u.email as email, i.photo_name as photoName from users as u join images i on u.image_id = i.id",
            countQuery = "select count(*) from products ",
            nativeQuery = true)
    Page<UserProjection> getUsers(Pageable pageable);

    @Query(value = "select u.id as id, u.full_name as fullName, u.email as email, i.photo_name as photoName from users as u join images i on u.image_id = i.id where u.id=:id",
            nativeQuery = true)
    Optional<UserProjection> getUserById(int id);

    @Query(value = "select id from users  where email = :email",nativeQuery = true)
    Integer checkToUnique(String email);

    Optional<User> findByEmail(String email);

    @Query(value = "select r.id as id, r.name as name from users u join user_role ur on u.id = ur.user_id join roles r on r.id = ur.role_id where u.id = :id"
    ,nativeQuery = true)
    List<RoleProjection> getUserRoles(int id);

    @Query(value = "select p.id as id, p.name as name from users u join user_permission up on u.id = up.user_id join permissions p on p.id = up.permission_id where u.id = :id"
            ,nativeQuery = true)
    List<RoleProjection> getUserPermissions(int id);
    @Modifying
    @Transactional
    @Query(nativeQuery = true,
    value = "update users set is_enabled = true where id = :id")
    void verify(Integer id);
}
