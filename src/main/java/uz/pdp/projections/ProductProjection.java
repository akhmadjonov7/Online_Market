package uz.pdp.projections;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public interface ProductProjection {
    Integer getId();
    String getName();
    Double getPrice();
    String getImageUrl();
}
