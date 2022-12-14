package uz.pdp.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.entities.Brand;
import uz.pdp.projections.BrandProjection;
import uz.pdp.projections.BrandProjectionById;

import java.util.Optional;


public interface BrandRepo extends JpaRepository<Brand,Integer> {

    @Query(value = "select b.id as id," +
            "b.name as name," +
            " i.photo_name as imagePath" +
            " from brands b join images i on b.image_id = i.id",
            countQuery = "select count(*) from brands",
            nativeQuery = true)
    Page<BrandProjection> getBrands(Pageable pageable);

    @Query(value = "select b.id as id," +
            "b.name as name," +
            "b.owner as owner," +
            " b.about as about," +
            "i.id as imageId," +
            "i.photo_name as imagePath" +
            " from brands b join images i on b.image_id = i.id where b.id = :id",
            nativeQuery = true)
    Optional<BrandProjectionById> getBrandById(int id);

    @Query(value = "select id from brands where name = :name",nativeQuery = true)
    Integer  getBrandByName(String name);
}
