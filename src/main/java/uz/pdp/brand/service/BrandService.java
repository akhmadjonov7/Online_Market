package uz.pdp.brand.service;

import jakarta.servlet.http.Part;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.brand.dto.BrandDto;
import uz.pdp.brand.model.Brand;
import uz.pdp.brand.repository.BrandRepo;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static uz.pdp.UploadDirectory.UPLOAD_DIRECTORY;

@RequiredArgsConstructor
@Service
public class BrandService {
    private final BrandRepo brandRepo;
    public void save(Brand brand) {
        brandRepo.save(brand);
    }

    public List<BrandDto> getAllBrands() {
        BrandDto brandDto = new BrandDto();
        for (Brand brand : brandRepo.findAll()) {
            String logo_url = brand.getLogo_url();

        }


        return null;
    }

    public void delete(int id) {
        brandRepo.deleteById(id);
    }

    public Brand getBrandById(int id) {
        Optional<Brand> brandById = brandRepo.findById(id);
        return brandById.get();
    }
    public String uploadAndGetPath(MultipartFile image){
        System.out.println("image.getOriginalFilename() = " + image.getOriginalFilename());
        File uploadDir = new File(UPLOAD_DIRECTORY);
        if (!uploadDir.exists())
            uploadDir.mkdirs();
        int index = image.getOriginalFilename().lastIndexOf('.');
        String extension = image.getOriginalFilename().substring(index + 1);
        String imgName = System.currentTimeMillis() + "." + extension;
        String imgPath = uploadDir.getPath() + "/" + imgName;
        try {
            image.transferTo(Path.of(imgPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return imgName;
    }
}
