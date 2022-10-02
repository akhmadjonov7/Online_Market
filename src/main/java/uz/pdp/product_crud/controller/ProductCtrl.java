package uz.pdp.product_crud.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.feature_crud.service.FeatureService;
import uz.pdp.product_crud.entity.Product;
import uz.pdp.product_crud.projection.ProductProjection;
import uz.pdp.product_crud.service.ProductService;
import uz.pdp.rest.Api;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductCtrl {
    private final ProductService productService;
    private final FeatureService featureService;
    @PostMapping
    public HttpEntity<?> addProduct(@RequestPart(name = "image",required = false) MultipartFile image, @RequestPart("product") String productJson){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Product product = objectMapper.readValue(productJson, Product.class);
            featureService.save(product.getFeature());
            productService.save(product);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(new Api("",true,null));
    }
    @GetMapping
    public HttpEntity<?> showProducts(@RequestParam (name = "page",defaultValue = "1") int page, @RequestParam(name = "size",defaultValue = "1") int size){
        List<ProductProjection> productProjections = productService.showProducts(page, size);
        if (productProjections.size()==0) {
            return ResponseEntity.ok(new Api("Not Found",true,null));
        }
        return ResponseEntity.ok(new Api("",true,productProjections));
    }
}
