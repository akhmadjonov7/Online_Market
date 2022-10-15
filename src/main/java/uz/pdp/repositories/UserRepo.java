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

    @Query(value = "select u.full_name as full_name,\n" +
            "       u.phone_number as phone_number\n" +
            "       from users as u",
            countQuery = "select count(*) from products ",
            nativeQuery = true)
    Page<UserProjection> getUsers(Pageable pageable);

    @Query(value = "select u.full_name as full_name,\n" +
            "       u.phone_number as phone_number\n" +
            "       from users as u\n" +
            "       where u.id=:id;",
            nativeQuery = true)
    Optional<UserProjection> getUserById(int id);
}
