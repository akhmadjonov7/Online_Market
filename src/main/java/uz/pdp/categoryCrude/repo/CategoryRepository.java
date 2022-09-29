package uz.pdp.categoryCrude.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.categoryCrude.model.Category;

public interface CategoryRepository extends JpaRepository<Category,Integer>{


}
