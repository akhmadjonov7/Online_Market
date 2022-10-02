package uz.pdp.brand_crud.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.brand_crud.model.Brand;
import uz.pdp.rest.Api;
import uz.pdp.brand_crud.service.BrandService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/brands")
public class BrandController {
    private final BrandService brandService;

    @PostMapping(value = {"/add", "/update"} , consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public HttpEntity<?> create(@RequestPart(name = "image",required = false) MultipartFile image, @RequestPart("brand") String brandJson){
        String imagePath = null;
        if (image!=null)
        imagePath = brandService.uploadAndGetPath(image);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Brand brand = objectMapper.readValue(brandJson,Brand.class);
            brand.setLogo_url(imagePath);
            brandService.save(brand);
        } catch (JsonProcessingException e) {
            return  ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(new Api("", true, null));
    }

    @GetMapping
    public HttpEntity<?> read(@RequestParam (name = "page",defaultValue = "1") int page, @RequestParam(name = "size",defaultValue = "1") int size) {
        Page<Brand> brandPage = brandService.getAllBrands(size, page);
        return ResponseEntity.ok(new Api("",true,brandPage));
    }

    @GetMapping("/delete/{id}")
    public HttpEntity<?> delete(@PathVariable int id) {
        brandService.delete(id);
        return ResponseEntity.ok(new Api("", true, null));
    }

    @GetMapping("/{id}")
    public HttpEntity<?> brandById(@PathVariable int id) {
        Brand brandById = brandService.getBrandById(id);
        if (brandById == null) {
            return ResponseEntity.ok(new Api("Not Found", false, null));
        }
        return ResponseEntity.ok(new Api("", true, brandById));
    }
}
