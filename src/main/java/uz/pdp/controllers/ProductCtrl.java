package uz.pdp.controllers;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN' , 'ROLE_SUPER_ADMIN')")
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
        if (productById==null) {
            return ResponseEntity.badRequest().body(new ApiResponse("Product not found",false,null));
        }
        return ResponseEntity.ok(new ApiResponse("", true, productById));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('CAN_DELETE_PRODUCT' , 'ROLE_SUPER_ADMIN')")
    public HttpEntity<?> deleteProduct(@PathVariable Integer id) {
        productService.delete(id);
        return ResponseEntity.ok(new ApiResponse("", true, null));
    }

    @SneakyThrows
    @Transactional
    @PutMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN' , 'ROLE_SUPER_ADMIN')")
    public HttpEntity<?> editProduct(@Valid @RequestPart("product") ProductDto productDto,BindingResult bindingResult,@RequestPart(name = "images", required = false) List<MultipartFile> imageList) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new ApiResponse("Validation",false,bindingResult.getAllErrors()));
        }
        if (productDto.getId()==null) {
            return ResponseEntity.badRequest().body(new ApiResponse("Something wrong",false,true));
        }
        ProductProjectionById productById = productService.getProductById(productDto.getId());
        if (!productById.getName().equals(productDto.getName())) if (productService.checkToUnique(productDto.getName())) {
            return ResponseEntity.badRequest().body(new ApiResponse("Error",false,"This product has already exists"));
        }
        Product edit = productService.edit(productDto, imageList);
        if (edit==null) {
            return ResponseEntity.badRequest().body(new ApiResponse("Product not found",false,null));
        }

        return ResponseEntity.ok(new ApiResponse("",true,null));
    }
}
