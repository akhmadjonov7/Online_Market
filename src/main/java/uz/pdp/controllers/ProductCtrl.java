package uz.pdp.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.entities.Brand;
import uz.pdp.entities.Category;
import uz.pdp.dtos.ProductDto;
import uz.pdp.entities.Product;
import uz.pdp.projections.ProductProjection;
import uz.pdp.projections.ProductProjectionById;
import uz.pdp.services.ProductService;
import uz.pdp.util.Api;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductCtrl {
    private final ProductService productService;
    @SneakyThrows
    @Transactional
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public HttpEntity<?> addProduct(@RequestPart(name = "images", required = false) List<MultipartFile> imageList,
                                    @RequestPart("product") String productJson) {
        ObjectMapper objectMapper = new ObjectMapper();
        ProductDto productDto = objectMapper.readValue(productJson, ProductDto.class);
        Product save = productService.save(productDto, imageList);
        if (save==null) {
            return ResponseEntity.ok(new Api("Not Added!!!",false,null));
        }
        return ResponseEntity.ok(new Api("Added!!!", true, null));
    }

    @GetMapping
    public HttpEntity<?> showProducts(@RequestParam(name = "page", defaultValue = "1") int page, @RequestParam(name = "size", defaultValue = "5") int size) {
        Page<ProductProjection> productProjections = null;
        try {
            productProjections = productService.showProducts(page, size);
        } catch (Exception e) {
            return ResponseEntity.ok(new Api("Not Found", true, null));
        }
        if (productProjections.isEmpty()) {
            return ResponseEntity.ok(new Api("Not Found", true, null));
        }
        return ResponseEntity.ok(new Api("", true, productProjections));
    }

    @GetMapping("/{id}")
    public HttpEntity<?> showProductById(@PathVariable Integer id) {
        ProductProjectionById productById = productService.getProductById(id);
        return ResponseEntity.ok(new Api("", true, productById));
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteProduct(@PathVariable Integer id) {
        productService.delete(id);
        return ResponseEntity.ok(new Api("", true, null));
    }

    @SneakyThrows
    @Transactional
    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public HttpEntity<?> editProduct(@RequestPart(name = "images", required = false) List<MultipartFile> imageList,
                                     @RequestPart("product") String productJson) {
        ObjectMapper objectMapper = new ObjectMapper();
        ProductDto productDto = objectMapper.readValue(productJson, ProductDto.class);
//        System.out.println("imageList.size() = " + imageList.size());
        Product edit = productService.edit(productDto, imageList);
        if (edit==null) {
            return ResponseEntity.ok(new Api("Product not found",false,null));
        }

        return ResponseEntity.ok(new Api("",true,null));
    }
    @SneakyThrows
    @PutMapping("/add/{id}")
    public HttpEntity<?> addToAmount(@RequestBody String amountJson, @PathVariable Integer id){
        ObjectMapper objectMapper = new ObjectMapper();
        ProductDto productDto = objectMapper.readValue(amountJson, ProductDto.class);
        Integer amount = productDto.getAmount();
        if (amount<0) {
            return ResponseEntity.ok(new Api("amount must not be less than zero",false,null));
        }
        boolean b = productService.addAmount(id, amount);
        if (b) {
            return ResponseEntity.ok(new Api("",true,null));
        }
        return ResponseEntity.ok(new Api("Product Not Found",false,null));
    }
}
