package uz.pdp.brand.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uz.pdp.brand.model.Brand;
import uz.pdp.brand.rest.api;
import uz.pdp.brand.service.BrandService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/brands")
public class BrandController {
    private final BrandService brandService;

    @PostMapping({"/add", "/update"})
    public HttpEntity<?> creat(@RequestBody Brand brand) {
        brandService.save(brand);
        return ResponseEntity.ok(new api("", true, null));
    }

    @GetMapping
    public HttpEntity<?> read() {
        List<Brand> brandList = brandService.getAllBrands();
        return ResponseEntity.ok(new api("", true, brandList));
    }

    @GetMapping("/delete/{id}")
    public HttpEntity<?> delete(@PathVariable int id) {
        brandService.delete(id);
        return ResponseEntity.ok(new api("", true, null));
    }

    @GetMapping("/{id}")
    public HttpEntity<?> brandById(@PathVariable int id) {
        Brand brandById = brandService.getBrandById(id);
        if (brandById == null) {
            return ResponseEntity.ok(new api("Not Found", false, null));
        }
        return ResponseEntity.ok(new api("", true, brandById));
    }
}
