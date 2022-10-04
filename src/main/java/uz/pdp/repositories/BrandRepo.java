package uz.pdp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.entities.Brand;


public interface BrandRepo extends JpaRepository<Brand,Integer> {
}
