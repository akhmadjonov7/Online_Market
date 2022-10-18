package uz.pdp.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.dtos.ProductDto;
import uz.pdp.entities.Product;
import uz.pdp.projections.ProductProjection;
import uz.pdp.projections.ProductProjectionById;
import uz.pdp.services.ProductService;
import uz.pdp.util.ApiResponse;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductCtrl {
    private final ProductService productService;
    @SneakyThrows
    @Transactional
    @PostMapping
    public HttpEntity<?> addProduct(@Valid @RequestPart("product") ProductDto productDto, BindingResult bindingResult, @RequestPart(name = "images", required = false) List<MultipartFile> imageList) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new ApiResponse("Validation",false,bindingResult.getAllErrors()));
        }
        if (productService.checkToUnique(productDto.getName())) {
            return ResponseEntity.badRequest().body(new ApiResponse("Error",false,"This product has already exists"));
        }
        Product save = productService.save(productDto, imageList);
        if (save==null) {
            return ResponseEntity.ok(new ApiResponse("Not Added!!!",false,null));
        }
        return ResponseEntity.ok(new ApiResponse("Added!!!", true, null));
    }

    @GetMapping
    public HttpEntity<?> showProducts(@RequestParam(name = "page", defaultValue = "1") int page, @RequestParam(name = "size", defaultValue = "5") int size) {
        if (page<=0) page = 1;
        if (size<=0) size = 5;
        Page<ProductProjection> productProjections = null;
        try {
            productProjections = productService.showProducts(page, size);
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse("Not Found", true, null));
        }
        if (productProjections.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse("Not Found", true, null));
        }
        return ResponseEntity.ok(new ApiResponse("", true, productProjections));
    }

    @GetMapping("/{id}")
    public HttpEntity<?> showProductById(@PathVariable Integer id) {
        ProductProjectionById productById = productService.getProductById(id);
        return ResponseEntity.ok(new ApiResponse("", true, productById));
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteProduct(@PathVariable Integer id) {
        productService.delete(id);
        return ResponseEntity.ok(new ApiResponse("", true, null));
    }

    @SneakyThrows
    @Transactional
    @PutMapping
    public HttpEntity<?> editProduct(@Valid @RequestPart("product") ProductDto productDto,BindingResult bindingResult,@RequestPart(name = "images", required = false) List<MultipartFile> imageList) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new ApiResponse("Validation",false,bindingResult.getAllErrors()));
        }
        if (productDto.getId()==null) {
            return ResponseEntity.badRequest().body(new ApiResponse("Something wrong",false,true));
        }
        if (productService.checkToUnique(productDto.getName())) {
            return ResponseEntity.badRequest().body(new ApiResponse("Error",false,"This product has already exists"));
        }
        Product edit = productService.edit(productDto, imageList);
        if (edit==null) {
            return ResponseEntity.badRequest().body(new ApiResponse("Product not found",false,null));
        }

        return ResponseEntity.ok(new ApiResponse("",true,null));
    }
    @SneakyThrows
    @PutMapping("/add/{id}")
    public HttpEntity<?> addToAmount(@RequestBody String amountJson, @PathVariable Integer id){
        ObjectMapper objectMapper = new ObjectMapper();
        ProductDto productDto = objectMapper.readValue(amountJson, ProductDto.class);
        Integer amount = productDto.getAmount();
        if (amount<=0) {
            return ResponseEntity.ok(new ApiResponse("amount must not be less than zero",false,null));
        }
        boolean b = productService.addAmount(id, amount);
        if (b) {
            return ResponseEntity.ok(new ApiResponse("",true,null));
        }
        return ResponseEntity.ok(new ApiResponse("Product Not Found",false,null));
    }
}
