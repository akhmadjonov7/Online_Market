package uz.pdp.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.projections.BrandProjection;
import uz.pdp.entities.Brand;
import uz.pdp.util.ApiResponse;
import uz.pdp.services.BrandService;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/brands")
public class BrandCtrl {
    private final BrandService brandService;

    @SneakyThrows
    @Transactional
    @PostMapping
    public HttpEntity<?> saveBrand(@Valid @RequestPart Brand brand, BindingResult bindingResult, @RequestPart(name = "image", required = false) MultipartFile image) {
        if (bindingResult.hasErrors())  return ResponseEntity.badRequest().body(new ApiResponse("Validation error", false, bindingResult.getAllErrors()));
        if (brandService.checkToUnique(brand.getName())) return ResponseEntity.badRequest().body(new ApiResponse("Error", false, "This brand  has already exists!!!"));
        brandService.save(brand, image);
        return ResponseEntity.ok(new ApiResponse("", true, null));
    }

    @GetMapping
    public HttpEntity<?> showBrands(@RequestParam(name = "page", defaultValue = "1") int page,
                                    @RequestParam(name = "size", defaultValue = "5") int size) {
        if (page <= 0) page = 1;
        if (size <= 0) size = 5;
        Page<BrandProjection> brandPage = brandService.getAllBrands(size, page);
        return ResponseEntity.ok(new ApiResponse("", true, brandPage));
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable int id) {
        boolean delete = brandService.delete(id);
        if (delete) return ResponseEntity.ok(new ApiResponse("", true, null));
        return ResponseEntity.badRequest().body(new ApiResponse("You cannot delete this brand", false, null));
    }

    @GetMapping("/{id}")
    public HttpEntity<?> brandById(@PathVariable int id) {
        BrandProjection brandById = brandService.getBrandById(id);
        if (brandById == null)  return ResponseEntity.badRequest().body(new ApiResponse("Not Found", false, null));
        return ResponseEntity.ok(new ApiResponse("", true, brandById));
    }

    @PutMapping
    public HttpEntity<?> editBrand(@Valid @RequestPart("brand") Brand brand, BindingResult bindingResult, @RequestPart(name = "image", required = false) MultipartFile image) {
        if (bindingResult.hasErrors())  return ResponseEntity.badRequest().body(new ApiResponse("Validation error", false, bindingResult.getAllErrors()));
        if (brandService.checkToUnique(brand.getName()))    return ResponseEntity.badRequest().body(new ApiResponse("Error", false, "This brand  has already exists!!!"));
        try {
            brandService.edit(brand, image);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse("Not Found", false, null));
        }
        return ResponseEntity.ok(new ApiResponse("", true, null));
    }
}
