package uz.pdp.feature_crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.feature_crud.entity.Feature;

import java.util.List;
import java.util.Optional;

public interface FeatureRepo extends JpaRepository<Feature,Integer> {
    @Query(value = "select * from features where product_id = :productId",nativeQuery = true)
    List<Feature> findFeaturesById(int productId);

}
