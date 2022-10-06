package uz.pdp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.entities.Feature;
import uz.pdp.projections.FeatureProjection;

import java.util.List;

public interface FeatureRepo extends JpaRepository<Feature,Integer> {
    @Query(value = "select id as id," +
            "title as title," +
            "name as name " +
            "from features where product_id = :productId",
            nativeQuery = true)
    List<FeatureProjection> findFeaturesById(int productId);

}
