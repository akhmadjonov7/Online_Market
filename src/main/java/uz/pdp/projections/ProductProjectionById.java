package uz.pdp.projections;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public interface ProductProjectionById {
    Integer getId();
    String getName();
    Double getPrice();
    Integer getAmount();
    @Value("#{@charactreristicChValueRepo.getAllCharacteristicsValuesByProductId(target.id)}")
    List<CharacteristicsValuesProjection> getCharacters();
    @Value("#{@imageRepo.getProductImages(target.id)}")
    List<ImageDataProjection> getImages();
    Integer getBrandId();
    String getBrandName();
    Integer getCategoryId();
    String getCategoryName();
}
