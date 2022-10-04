package uz.pdp.feature_crud.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.feature_crud.entity.Feature;
import uz.pdp.feature_crud.repository.FeatureRepo;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class FeatureService {
    private final FeatureRepo featureRepo;
    public void save(List<Feature> featureList) {
        for (Feature feature : featureList) {
            System.out.println(feature);
        }

        featureRepo.saveAll(featureList);
    }

    public void delete(int id) {
            featureRepo.deleteById(id);
    }

    public Optional<Feature> getFeatureById(int id) {
        Optional<Feature> featureById = featureRepo.findById(id);
        return featureById;
    }

    public List<Feature> getProductFeatures(int productId){
        return featureRepo.findFeaturesById(productId);
    }
}
