package uz.pdp.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.entities.ImageData;
import uz.pdp.projections.ImageDataProjection;
import uz.pdp.services.ImageService;
import uz.pdp.entities.Brand;
import uz.pdp.entities.Category;
import uz.pdp.entities.Feature;
import uz.pdp.services.FeatureService;
import uz.pdp.dtos.ProductDto;
import uz.pdp.entities.Product;
import uz.pdp.projections.ProductProjection;
import uz.pdp.services.ProductService;
import uz.pdp.util.Api;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static uz.pdp.util.UploadDirectory.UPLOAD_DIRECTORY;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductCtrl {
    private final ProductService productService;
    private final FeatureService featureService;
    private final ImageService imageService;
    @SneakyThrows
    @Transactional
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public HttpEntity<?> addProduct(@RequestPart(name = "images",required = false) List<MultipartFile> imageList,
                                    @RequestPart("product") String productJson){
        ObjectMapper objectMapper = new ObjectMapper();
            ProductDto productDto = objectMapper.readValue(productJson, ProductDto.class);
            Product product = Product.builder()
                    .id(productDto.getId())
                    .name(productDto.getName())
                    .price(productDto.getPrice())
                    .amount(productDto.getAmount())
                    .brand(Brand.builder().id(productDto.getBrandId()).build())
                    .category(Category.builder().id(productDto.getCategoryId()).build())
                    .feature(productDto.getFeature())
                    .build();
        if (imageList.size()==0) {
            Path path = Paths.get("src/main/resources/image/download.png");
            MultipartFile defaultImage = new MockMultipartFile("download.png", "download.png",
                    "image/png", Files.readAllBytes(path));
            ImageData save = imageService.save(defaultImage);
            product.setImage((List.of(save)));
        }else {
            List<ImageData> save = imageService.saveAll(imageList);
            product.setImage(save);
        }
        Product save = productService.save(product);
        for (Feature feature : product.getFeature()) {
                feature.setProduct(Product.builder().id(save.getId()).build());
            }
            featureService.save(product.getFeature());
        return ResponseEntity.ok(new Api("",true,null));
    }
    @GetMapping
    public HttpEntity<?> showProducts(@RequestParam (name = "page",defaultValue = "1") int page, @RequestParam(name = "size",defaultValue = "5") int size){
        Page<ProductProjection> productProjections = productService.showProducts(page, size);
        if (productProjections.isEmpty()) {
            return ResponseEntity.ok(new Api("Not Found",true,null));
        }
        return ResponseEntity.ok(new Api("",true,productProjections));
    }
    @GetMapping("/{id}")
    public HttpEntity<?> showProductById(@PathVariable Integer id){
        ProductDto productById = productService.getProductById(id);
        return ResponseEntity.ok(new Api("",true,productById));
    }
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteProduct(@PathVariable Integer id){
        productService.delete(id);
        return ResponseEntity.ok(new Api("",true,null));
    }

    @SneakyThrows
    @Transactional
    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public HttpEntity<?> editProduct(@RequestPart(name = "images",required = false) List<MultipartFile> imageList,
                                     @RequestPart("product") String productJson){
        ObjectMapper objectMapper = new ObjectMapper();
        ProductDto productDto = objectMapper.readValue(productJson, ProductDto.class);
        Product product = Product.builder()
                .id(productDto.getId())
                .name(productDto.getName())
                .price(productDto.getPrice())
                .amount(productDto.getAmount())
                .brand(Brand.builder().id(productDto.getBrandId()).build())
                .category(Category.builder().id(productDto.getCategoryId()).build())
                .feature(productDto.getFeature())
                .build();
        return null;
    }
}
