package uz.pdp.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.entities.Brand;
import uz.pdp.projections.BrandProjection;
import uz.pdp.repositories.BrandRepo;

import java.io.*;
import java.util.List;
import java.util.Optional;

import static uz.pdp.util.UploadDirectory.UPLOAD_DIRECTORY;

@RequiredArgsConstructor
@Service
public class BrandService {
    private final BrandRepo brandRepo;
    public void save(Brand brand) {
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
        System.out.println(brandById.get());
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
