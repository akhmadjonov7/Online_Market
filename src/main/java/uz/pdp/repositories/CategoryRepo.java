package uz.pdp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.entities.Category;

public interface CategoryRepo extends JpaRepository<Category,Integer>{


    @Query(value = "select id from categories where name = :name",nativeQuery = true)
    Integer checkToUnique(String name);
}
