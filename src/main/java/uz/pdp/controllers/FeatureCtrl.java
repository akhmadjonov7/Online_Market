package uz.pdp.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.services.FeatureService;

@RestController
@RequestMapping("/features")
@RequiredArgsConstructor
public class FeatureCtrl {
    private final FeatureService featureService;

}
