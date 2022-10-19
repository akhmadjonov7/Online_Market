package uz.pdp.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.projections.ProductProjection;
import uz.pdp.projections.ProductProjectionById;
import uz.pdp.projections.UserProjection;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Integer> {

    @Query(value = "select u.full_name as fullName,\n" +
            "       u.username as username\n" +
            "       from users as u",
            countQuery = "select count(*) from products ",
            nativeQuery = true)
    Page<UserProjection> getUsers(Pageable pageable);

    @Query(value = "select u.full_name as fullName, u.username as username, i.photo_name as photoName from users as u join images i on u.image_id = i.id where u.id=:id;",
            nativeQuery = true)
    Optional<UserProjection> getUserById(int id);

    @Query(value = "select id from users  where phone_number = :phoneNumber",nativeQuery = true)
    Integer checkToUnique(String phoneNumber);

    Optional<User> findByUsername(String username);
}
