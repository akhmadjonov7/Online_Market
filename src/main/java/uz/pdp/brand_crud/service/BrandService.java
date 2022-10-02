package uz.pdp.brand_crud.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.brand_crud.model.Brand;
import uz.pdp.brand_crud.repository.BrandRepo;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Path;
import java.util.Optional;

import static uz.pdp.UploadDirectory.UPLOAD_DIRECTORY;

@RequiredArgsConstructor
@Service
public class BrandService {
    private final BrandRepo brandRepo;
    public void save(Brand brand) {
        brandRepo.save(brand);
    }

    public Page<Brand> getAllBrands(int size, int page) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Brand> brandPage = brandRepo.findAll(pageable);
        return brandPage;
    }

    public boolean delete(int id) {
        try {
            String logo_url = brandRepo.removeBrandById(id);
            File file = new File(UPLOAD_DIRECTORY + logo_url);
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
    public String uploadAndGetPath(MultipartFile image){
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
    @SneakyThrows
    public MultipartFile getImage(String name){
        if (name==null) {
            return null;
        }
        BufferedImage image = null;
        File file = new File(UPLOAD_DIRECTORY + name);
        image = ImageIO.read(file);
        System.out.println(image.getHeight());
        return (MultipartFile) image;
    }
}
