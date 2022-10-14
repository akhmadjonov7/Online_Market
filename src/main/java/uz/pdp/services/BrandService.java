package uz.pdp.services;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.entities.Brand;
import uz.pdp.entities.ImageData;
import uz.pdp.projections.BrandProjection;
import uz.pdp.repositories.BrandRepo;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static uz.pdp.util.UploadDirectory.UPLOAD_DIRECTORY;

@RequiredArgsConstructor
@Service
public class BrandService {
    private final BrandRepo brandRepo;
    private final ImageService imageService;
    @SneakyThrows
    public void save(Brand brand, MultipartFile image) {
        if (image != null) {
            ImageData logo = imageService.save(image);
            brand.setImage(logo);
        } else {
            Path path = Paths.get("src/main/resources/image/download.png");
            MultipartFile defaultImage = new MockMultipartFile("download.png", "download.png",
                    "image/png", Files.readAllBytes(path));
            ImageData save = imageService.save(defaultImage);
            brand.setImage(save);
        }
        brandRepo.save(brand);
    }

    public Page<BrandProjection> getAllBrands(int size, int page){
        Page<BrandProjection> brandPage = brandRepo.getBrands(PageRequest.of(page-1, size));
        if (brandPage.isEmpty()) {
            return null;
        }
        return brandPage;
    }

    public boolean delete(int id) {
        try {
            Optional<Brand> brand = brandRepo.findById(id);
            brandRepo.deleteById(id);
            File file = new File(UPLOAD_DIRECTORY + brand.get().getImage().getPhotoName());
            file.delete();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public BrandProjection getBrandById(int id) {
        Optional<BrandProjection> brandById = brandRepo.getBrandById(id);
        if (brandById.isEmpty()) {
            return null;
        }
        return brandById.get();
    }
    public BrandProjection getBrandByIdWithoutImage(int id) {
        Optional<BrandProjection> brandById = brandRepo.getBrandByIdWithoutImage(id);
        if (brandById.isEmpty()) {
            return null;
        }
        return brandById.get();
    }
    public Integer getBrandImageId(Integer id){
        return brandRepo.getBrandImageId(id);
    }
}
