package uz.pdp.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.entities.Brand;
import uz.pdp.repositories.BrandRepo;

import java.io.*;
import java.util.Optional;

import static uz.pdp.util.UploadDirectory.UPLOAD_DIRECTORY;

@RequiredArgsConstructor
@Service
public class BrandService {
    private final BrandRepo brandRepo;
    public void save(Brand brand) {
        brandRepo.save(brand);
    }

    public Page<Brand> getAllBrands(int size, int page){
        Pageable pageable = PageRequest.of(page-1,size);
        Page<Brand> brandPage = brandRepo.findAll(pageable);
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

    public Brand getBrandById(int id) {
        Optional<Brand> brandById = brandRepo.findById(id);
        return brandById.get();
    }
}
