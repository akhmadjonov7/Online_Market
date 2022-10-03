package uz.pdp.brand_crud.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.attechment.model.ImageData;
import uz.pdp.attechment.service.ImageService;
import uz.pdp.brand_crud.model.Brand;
import uz.pdp.rest.Api;
import uz.pdp.brand_crud.service.BrandService;

import java.io.File;

import static uz.pdp.UploadDirectory.UPLOAD_DIRECTORY;

@Controller
@RequiredArgsConstructor
@RequestMapping("/brands")
public class BrandController {
    private final BrandService brandService;
    private final ImageService imageService;
    @PostMapping(value = "/add" , consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public HttpEntity<?> create(@RequestPart(name = "image",required = false) MultipartFile image, @RequestPart("brand") String brandJson){

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Brand brand = objectMapper.readValue(brandJson,Brand.class);
            if (image!=null) {
                ImageData logo = imageService.save(image);
                brand.setImage(logo);
            }
            brandService.save(brand);
        } catch (JsonProcessingException e) {
            return  ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(new Api("", true, null));
    }

    @GetMapping
    public HttpEntity<?> read(@RequestParam (name = "page",defaultValue = "1") int page, @RequestParam(name = "size",defaultValue = "1") int size) {
        Page<Brand> brandPage = brandService.getAllBrands(size, page);
        for (Brand brand : brandPage.getContent()) {
            imageService.getImage(brand.getImage().getPhotoName());
        }
        return ResponseEntity.ok(new Api("",true,brandPage));
    }

    @DeleteMapping("/delete/{id}")
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
    @SneakyThrows
    @PutMapping(value = "/update" , consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public HttpEntity<?> editBrand(@RequestPart(name = "image",required = false) MultipartFile image, @RequestPart("brand") String brandJson){
        ObjectMapper objectMapper = new ObjectMapper();
        Brand brand = objectMapper.readValue(brandJson, Brand.class);
        Brand brandById = brandService.getBrandById(brand.getId());
        if (image!=null)
            image.transferTo(new File(UPLOAD_DIRECTORY + brandById.getImage().getPhotoName()));
        brand.setImage(brandById.getImage());
        brandService.save(brand);
        return ResponseEntity.ok(new Api("", true, null));
    }
}
