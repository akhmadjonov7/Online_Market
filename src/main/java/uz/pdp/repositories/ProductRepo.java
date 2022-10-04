package uz.pdp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.entities.Product;
import uz.pdp.projections.ProductProjection;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product, Integer> {
    @Query(value = "select p.id, p.name,p.price,i.photo_name as imgUrl from products p join products_image pi on p.id = pi.products_id join images i on pi.image_id = i.id limit :size offset (:page - 1 ) * :size",nativeQuery = true)
    List<ProductProjection> getProducts(int page, int size);
}
