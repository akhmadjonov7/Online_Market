package uz.pdp.attechment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.attechment.model.ImageData;
import uz.pdp.attechment.projection.ImageDataProjection;

public interface ImageRepo extends JpaRepository<ImageData, Integer> {

    @Query(value = "select id, photo_name as photoName, content_type as contentType from images where photo_name = :photoName",nativeQuery = true)
    ImageDataProjection showImageDataByPhotoName(String photoName);
}
