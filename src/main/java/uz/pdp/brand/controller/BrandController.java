package uz.pdp.brand.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.brand.dto.BrandDto;
import uz.pdp.brand.model.Brand;
import uz.pdp.rest.Api;
import uz.pdp.brand.service.BrandService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/brands")
public class BrandController {
    private final BrandService brandService;

    @PostMapping(value = {"/add", "/update"} , consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public HttpEntity<?> create(@RequestPart("image") MultipartFile image, @RequestPart("brand") String brandJson){
        String imagePath = brandService.uploadAndGetPath(image);
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
    public HttpEntity<?> read() {
        List<BrandDto> brandList = brandService.getAllBrands();
        return ResponseEntity.ok(new Api("", true, brandList));
    }
//
//    @GetMapping("/delete/{id}")
//    public HttpEntity<?> delete(@PathVariable int id) {
//        brandService.delete(id);
//        return ResponseEntity.ok(new Api("", true, null));
//    }
//
//    @GetMapping("/{id}")
//    public HttpEntity<?> brandById(@PathVariable int id) {
//        BrandDto brandById = brandService.getBrandById(id);
//        if (brandById == null) {
//            return ResponseEntity.ok(new Api("Not Found", false, null));
//        }
//        return ResponseEntity.ok(new Api("", true, brandById));
//    }
}
