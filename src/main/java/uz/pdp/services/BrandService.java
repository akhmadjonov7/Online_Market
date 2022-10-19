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
import uz.pdp.repositories.ImageRepo;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static uz.pdp.util.Util.UPLOAD_DIRECTORY;

@RequiredArgsConstructor
@Service
public class BrandService {
    private final BrandRepo brandRepo;
    private final ImageService imageService;
    private final ImageRepo imageRepo;

    public  boolean checkToUnique(String name) {
        Integer brandByName = brandRepo.getBrandByName(name);
        return brandByName!=null;
    }

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
        if (brandPage.isEmpty())    return null;
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
        if (brandById.isEmpty())    return null;
        return brandById.get();
    }

    @SneakyThrows
    public void edit(Brand brand, MultipartFile image) {
        BrandProjection brandById = getBrandById(brand.getId());
        imageRepo.deleteById(brandById.getImageId());
        File file = new File(UPLOAD_DIRECTORY + brandById.getImagePath());
        file.delete();
        save(brand, image);
    }
}
