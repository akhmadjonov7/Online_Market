package uz.pdp.controllers;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.projections.BrandProjection;
import uz.pdp.entities.Brand;
import uz.pdp.projections.BrandProjectionById;
import uz.pdp.repositories.ImageRepo;
import uz.pdp.services.ImageService;
import uz.pdp.util.ApiResponse;
import uz.pdp.services.BrandService;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/brands")
public class BrandCtrl {
    private final BrandService brandService;
    private final ImageRepo imageRepo;

    @SneakyThrows
    @Transactional
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN' , 'ROLE_SUPER_ADMIN')")
    public HttpEntity<?> saveBrand(@Valid @RequestPart Brand brand, BindingResult bindingResult, @RequestPart(name = "image", required = false) MultipartFile image) {
        if (bindingResult.hasErrors())  return ResponseEntity.badRequest().body(new ApiResponse("Validation error", false, bindingResult.getAllErrors()));
        if (brandService.checkToUnique(brand.getName())) return ResponseEntity.badRequest().body(new ApiResponse("Error", false, "This brand  has already exists!!!"));
        brandService.save(brand, image);
        return ResponseEntity.ok(new ApiResponse("Brand saved!!!", true, null));
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
    @PreAuthorize("hasAnyAuthority('CAN_DELETE_BRAND' , 'ROLE_SUPER_ADMIN')")

    public HttpEntity<?> delete(@PathVariable int id) {
        boolean delete = brandService.delete(id);
        if (delete) return ResponseEntity.ok(new ApiResponse("", true, null));
        return ResponseEntity.badRequest().body(new ApiResponse("You cannot delete this brand", false, null));
    }

    @GetMapping("/{id}")
    public HttpEntity<?> brandById(@PathVariable int id) {
        BrandProjectionById brandById = brandService.getBrandById(id);
        if (brandById == null)  return ResponseEntity.badRequest().body(new ApiResponse("Not Found", false, null));
        return ResponseEntity.ok(new ApiResponse("", true, brandById));
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN' , 'ROLE_SUPER_ADMIN')")
    public HttpEntity<?> editBrand(@Valid @RequestPart("brand") Brand brand, BindingResult bindingResult, @RequestPart(name = "image", required = false) MultipartFile image) {
        if (bindingResult.hasErrors())  return ResponseEntity.badRequest().body(new ApiResponse("Validation error", false, bindingResult.getAllErrors()));
        BrandProjectionById brandById = brandService.getBrandById(brand.getId());
        if (!brandById.getName().equals(brand.getName())) if (brandService.checkToUnique(brand.getName()))    return ResponseEntity.badRequest().body(new ApiResponse("Error", false, "This brand  has already exists!!!"));
        try {
            brandService.edit(brand, image);
            imageRepo.deleteById(brandById.getImageId());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse("Not Found", false, null));
        }
        return ResponseEntity.ok(new ApiResponse("", true, null));
    }
}
