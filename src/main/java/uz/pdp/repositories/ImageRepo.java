package uz.pdp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.entities.ImageData;
import uz.pdp.projections.ImageDataProjection;

import java.util.List;

public interface ImageRepo extends JpaRepository<ImageData, Integer> {

    @Query(value = "select id," +
            " photo_name as photoName," +
            " content_type as contentType from images where photo_name = :photoName",
            nativeQuery = true)
    ImageDataProjection showImageDataByPhotoName(String photoName);

    @Query(value = "select i.id as id" +
            ", i.photo_name as photoName" +
            " from images i join products_image pi on i.id = pi.image_id where pi.products_id = :id",
            nativeQuery = true)
    List<ImageDataProjection> getProductImages(Integer id);




    @Query(value = "delete from images where id = :imageId",
    nativeQuery = true)
    void deleteProductImages(Integer imageId);
}
