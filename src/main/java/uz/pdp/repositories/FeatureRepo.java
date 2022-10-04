package uz.pdp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.entities.Feature;

import java.util.List;

public interface FeatureRepo extends JpaRepository<Feature,Integer> {
    @Query(value = "select * from features where product_id = :productId",nativeQuery = true)
    List<Feature> findFeaturesById(int productId);

}
