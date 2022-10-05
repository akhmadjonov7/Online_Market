package uz.pdp.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.entities.ImageData;
import uz.pdp.projections.BrandProjection;
import uz.pdp.services.ImageService;
import uz.pdp.entities.Brand;
import uz.pdp.util.Api;
import uz.pdp.services.BrandService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static uz.pdp.util.UploadDirectory.UPLOAD_DIRECTORY;

@Controller
@RequiredArgsConstructor
@RequestMapping("/brands")
public class BrandCtrl {
    private final BrandService brandService;
    private final ImageService imageService;

    @SneakyThrows
    @Transactional
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public HttpEntity<?> create(@RequestPart(name = "image", required = false) MultipartFile image, @RequestPart("brand") String brandJson) {
        ObjectMapper objectMapper = new ObjectMapper();
        Brand brand = objectMapper.readValue(brandJson, Brand.class);
        if (image != null) {
            ImageData logo = imageService.save(image);
            brand.setImage(logo);
        } else {
            Path path = Paths.get("src/main/resources/image/download.png");
            MultipartFile defaultImage = new MockMultipartFile("download.png", "download.png", "image/png", Files.readAllBytes(path));
            ImageData save = imageService.save(defaultImage);
            brand.setImage(save);
        }
        brandService.save(brand);
        return ResponseEntity.ok(new Api("", true, null));
    }

    @GetMapping
    public HttpEntity<?> read(@RequestParam(name = "page", defaultValue = "1") int page, @RequestParam(name = "size", defaultValue = "5") int size) {
        Page<BrandProjection> brandPage = brandService.getAllBrands(size, page);
        return ResponseEntity.ok(new Api("", true, brandPage));
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable int id) {
        boolean delete = brandService.delete(id);
        if (delete) {
            return ResponseEntity.ok(new Api("", true, null));
        }
        return ResponseEntity.ok(new Api("", false, null));
    }

    @GetMapping("/{id}")
    public HttpEntity<?> brandById(@PathVariable int id) {
        BrandProjection brandById = brandService.getBrandById(id);
        if (brandById == null) {
            return ResponseEntity.ok(new Api("Not Found", false, null));
        }
        return ResponseEntity.ok(new Api("", true, brandById));
    }

    @SneakyThrows
    @Transactional
    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public HttpEntity<?> editBrand(@RequestPart(name = "image", required = false) MultipartFile image, @RequestPart("brand") String brandJson) {
        ObjectMapper objectMapper = new ObjectMapper();
        Brand brand = objectMapper.readValue(brandJson, Brand.class);
        BrandProjection brandById = brandService.getBrandById(brand.getId());
        if (image != null) {
                image.transferTo(new File(UPLOAD_DIRECTORY + brandById.getImagePath()));
                brand.setImage(new ImageData(brandById.getImageId(), brandById.getImagePath(), image.getContentType()));
        } else {
            brand.setImage(new ImageData(brandById.getImageId(), brandById.getImagePath(), brandById.getImageContentType()));
        }
        brandService.save(brand);
        return ResponseEntity.ok(new Api("", true, null));
    }
}
