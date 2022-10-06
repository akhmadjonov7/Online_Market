package uz.pdp.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uz.pdp.dtos.ProductDto;
import uz.pdp.entities.Feature;
import uz.pdp.projections.FeatureProjection;
import uz.pdp.projections.ImageDataProjection;
import uz.pdp.repositories.FeatureRepo;
import uz.pdp.entities.Product;
import uz.pdp.projections.ProductProjection;
import uz.pdp.repositories.ImageRepo;
import uz.pdp.repositories.ProductRepo;

import java.io.File;
import java.util.List;
import java.util.Optional;

import static uz.pdp.util.UploadDirectory.UPLOAD_DIRECTORY;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepo productRepo;
    private final FeatureRepo featureRepo;
    private final ImageRepo imageRepo;
    public Product save(Product product){
        try {
            Product save = productRepo.save(product);
            return save;
        } catch (Exception e) {
            return null;
        }
    }
    public void delete(int id){
        for (ImageDataProjection imageDataProjection : imageRepo.getProductImages(id)) {
            File file = new File(UPLOAD_DIRECTORY + imageDataProjection.getPhotoName());
            file.delete();
        }
        productRepo.deleteById(id);
    }
    public ProductDto getProductById(int id){
        Optional<ProductProjection> productOptional = productRepo.getProductById(id);
        if (productOptional.isEmpty()) {
            return null;
        }

        ProductProjection productProjection = productOptional.get();
        ProductDto productDto = ProductDto
                .builder()
                .id(productProjection.getId())
                .name(productProjection.getName())
                .price(productProjection.getPrice())
                .brandId(productProjection.getBrandId())
                .brandName(productProjection.getBrandName())
                .categoryId(productProjection.getCategoryId())
                .categoryName(productProjection.getCategoryName())
                .build();
        List<FeatureProjection> featuresById = featureRepo.findFeaturesById(id);
        productDto.setFeatures(featuresById);
        List<ImageDataProjection> productImages = imageRepo.getProductImages(id);
        productDto.setImages(productImages);
        return productDto;
    }

    public Page<ProductProjection> showProducts(int page, int size) {
        Page<ProductProjection> products = productRepo.getProducts(PageRequest.of(page-1,size));
        return products;
    }
}
