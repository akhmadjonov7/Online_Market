package uz.pdp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.entities.Category;

public interface CategoryRepo extends JpaRepository<Category,Integer>{


}
