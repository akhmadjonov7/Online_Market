package uz.pdp.projections;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public interface ProductProjection {
    Integer getId();
    String getName();
    Double getPrice();

    @Value("#{@charactreristicChValueRepo.getAllCharacteristicsValuesByProductId(target.id)}")
    List<CharacteristicsValuesProjection> getCharacters();
    @Value("#{@imageRepo.getProductFirstImage(target.id)}")
    ImageDataProjection getImages();
    Integer getBrandId();
    String getBrandName();
    Integer getCategoryId();
    String getCategoryName();
}
