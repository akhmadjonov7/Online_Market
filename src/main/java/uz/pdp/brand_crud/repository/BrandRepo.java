package uz.pdp.brand_crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.brand_crud.model.Brand;


public interface BrandRepo extends JpaRepository<Brand,Integer> {
}
