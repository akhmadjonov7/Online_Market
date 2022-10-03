package uz.pdp.product_crud.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.attechment.model.ImageData;
import uz.pdp.attechment.service.ImageService;
import uz.pdp.brand_crud.model.Brand;
import uz.pdp.category_crud.model.Category;
import uz.pdp.feature_crud.entity.Feature;
import uz.pdp.feature_crud.service.FeatureService;
import uz.pdp.product_crud.dto.ProductDto;
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
    private final ImageService imageService;
    @PostMapping
    public HttpEntity<?> addProduct(@RequestPart(name = "images",required = false) List<MultipartFile> imageList, @RequestPart("product") String productJson){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ProductDto productDto = objectMapper.readValue(productJson, ProductDto.class);
            List<ImageData> save = imageService.saveAll(imageList);
            Product product = Product
                    .builder()
                    .id(productDto.getId())
                    .name(productDto.getName())
                    .price(productDto.getPrice())
                    .amount(productDto.getAmount())
                    .brand(Brand.builder().id(productDto.getBrandId()).build())
                    .category(Category.builder().id(productDto.getCategoryId()).build())
                    .feature(productDto.getFeature())
                    .build();
            product.setImage(save);
            productService.save(product);
            for (Feature feature : product.getFeature()) {
                feature.setProduct(Product.builder().id(product.getId()).build());
            }
            featureService.save(product.getFeature());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(new Api("",true,null));
    }
    @GetMapping
    public HttpEntity<?> showProducts(@RequestParam (name = "page",defaultValue = "1") int page, @RequestParam(name = "size",defaultValue = "5") int size){
        List<ProductProjection> productProjections = productService.showProducts(page, size);
        if (productProjections.size()==0) {
            return ResponseEntity.ok(new Api("Not Found",true,null));
        }
        return ResponseEntity.ok(new Api("",true,productProjections));
    }
    @GetMapping("/{id}")
    public HttpEntity<?> showProductById(@PathVariable Integer id){
        Product productById = productService.getProductById(id);
        return ResponseEntity.ok(new Api("",true,productById));
    }
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteProduct(@PathVariable Integer id){
        productService.delete(id);
        return ResponseEntity.ok(new Api("",true,null));
    }
}
