package uz.pdp.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.entities.Brand;
import uz.pdp.projections.BrandProjection;

import java.util.List;
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
            "i.photo_name as imagePath," +
            " from brands b join images i on b.image_id = i.id where b.id = :id",
            nativeQuery = true)
    Optional<BrandProjection> getBrandById(int id);

    @Query(value = "select b.id as id," +
            "b.name as name," +
            "b.owner as owner, " +
            "b.about as about where b.id = :id",
            nativeQuery = true)
    Optional<BrandProjection> getBrandByIdWithoutImage(int id);


    @Query(value = "select image_id from brands where id = :id",
            nativeQuery = true)
    Integer getBrandImageId(Integer id);
}
