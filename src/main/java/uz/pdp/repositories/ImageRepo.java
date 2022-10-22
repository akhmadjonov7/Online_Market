package uz.pdp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.entities.ImageData;
import uz.pdp.projections.ImageDataProjection;

import java.util.List;

public interface ImageRepo extends JpaRepository<ImageData, Integer> {
    @Query(value = "select i.id as id" +
            ", i.photo_name as photoName" +
            " from images i join products_image pi on i.id = pi.image_id where pi.products_id = :id",
            nativeQuery = true)
    List<ImageDataProjection> getProductImages(Integer id);
}
