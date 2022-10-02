package uz.pdp.product_crud.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uz.pdp.feature_crud.repository.FeatureRepo;
import uz.pdp.product_crud.entity.Product;
import uz.pdp.product_crud.projection.ProductProjection;
import uz.pdp.product_crud.repository.ProductRepo;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepo productRepo;
    private final FeatureRepo featureRepo;
    public boolean save(Product product){
        try {
            productRepo.save(product);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public void delete(int id){
        productRepo.deleteById(id);
    }
    public Product getProductById(int id){
        Optional<Product> productOptional = productRepo.findById(id);
        if (productOptional.isEmpty()) {
            return null;
        }
        Product product = productOptional.get();
        product.setFeature(featureRepo.findFeaturesById(id));
        return product;
    }

    public List<ProductProjection> showProducts(int page, int size) {
        List<ProductProjection> products = productRepo.getProducts(page, size);
        return products;
    }
}
