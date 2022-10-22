package uz.pdp.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.projections.UserProjection;

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


    @Modifying
    @Transactional
    @Query(nativeQuery = true,
    value = "update users set is_enabled = true where id = :id")
    void verify(Integer id);
}
