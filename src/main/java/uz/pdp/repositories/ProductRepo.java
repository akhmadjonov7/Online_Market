package uz.pdp.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.entities.Product;
import uz.pdp.projections.ImageDataProjection;
import uz.pdp.projections.ProductProjection;
import uz.pdp.projections.ProductProjectionById;

import java.util.List;
import java.util.Optional;

public interface ProductRepo extends JpaRepository<Product, Integer> {
    @Query(value = "select p.id as id , p.name as name ,p.price as price, i.photo_name as imageUrl from products p" +
            " join images i on p.main_image_id = i.id",
            countQuery = "select count(*) from products ",
            nativeQuery = true)
    Page<ProductProjection> getProducts(Pageable pageable);


    @Query(value = "select p.id as id," +
            "p.name as name," +
            "p.price as price," +
            "b.id as brandId," +
            "b.name as brandName," +
            "c.id as categoryId," +
            "c.name as categoryName" +
            " from products p join brands b on p.brand_id = b.id" +
            " join categories c on p.category_id = c.id where p.id = :id",nativeQuery = true)
    Optional<ProductProjectionById> getProductById(int id);

    @Query(value = "delete from products_image where image_id = :id",nativeQuery = true)
    void removeImage(Integer id);

    @Query(value = "delete from products_characteristics_ch_values where products_id = :id",
    nativeQuery = true)
    void removeCharacteristicsByProductId(Integer id);

}
