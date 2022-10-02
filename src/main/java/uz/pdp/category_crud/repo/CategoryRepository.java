package uz.pdp.category_crud.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.category_crud.model.Category;

public interface CategoryRepository extends JpaRepository<Category,Integer>{


}
