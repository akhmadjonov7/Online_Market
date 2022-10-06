package uz.pdp.projections;

import uz.pdp.entities.Feature;

import java.util.List;

public interface ProductProjection {
    Integer getId();
    String getName();
    Double getPrice();
    String getImgUrl();
    Integer getBrandId();
    String getBrandName();
    Integer getCategoryId();
    String getCategoryName();
}
