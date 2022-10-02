package uz.pdp.product_crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.product_crud.entity.Product;
import uz.pdp.product_crud.projection.ProductProjection;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product, Integer> {
    @Query(value = "select id,name,price,img_url as imgUrl from products limit :size offset (:page - 1 * :size) ",nativeQuery = true)
    List<ProductProjection> getProducts(int page, int size);
}
