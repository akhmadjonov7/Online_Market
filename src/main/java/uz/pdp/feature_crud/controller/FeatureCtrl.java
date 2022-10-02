package uz.pdp.feature_crud.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.feature_crud.entity.Feature;
import uz.pdp.feature_crud.repository.FeatureRepo;
import uz.pdp.feature_crud.service.FeatureService;

import java.util.List;

@RestController
@RequestMapping("/features")
@RequiredArgsConstructor
public class FeatureCtrl {
    private final FeatureService featureService;

}
