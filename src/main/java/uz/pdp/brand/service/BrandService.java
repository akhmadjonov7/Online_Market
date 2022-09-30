package uz.pdp.brand.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.brand.model.Brand;
import uz.pdp.brand.repository.BrandRepo;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BrandService {
    private final BrandRepo brandRepo;

    public void save(Brand brand) {
        brandRepo.save(brand);
    }

    public List<Brand> getAllBrands() {
        return brandRepo.findAll();
    }

    public void delete(int id) {
        brandRepo.deleteById(id);
    }

    public Brand getBrandById(int id) {
        Optional<Brand> brandById = brandRepo.findById(id);
        return brandById.get();
    }
}
