package uz.pdp.brand.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.brand.model.Brand;

public interface BrandRepo extends JpaRepository<Brand,Integer> {
}
