package uz.pdp.brand_crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.brand_crud.model.Brand;


public interface BrandRepo extends JpaRepository<Brand,Integer> {
    @Query(value = "delete from brands where id = :id returning logo_url", nativeQuery = true)
    String removeBrandById(Integer id);
}
