package uz.pdp.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.entities.Product;
import uz.pdp.projections.ProductProjection;
import uz.pdp.projections.ProductProjectionById;

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
            "p.amount as amount," +
            "b.id as brandId," +
            "b.name as brandName," +
            "c.id as categoryId," +
            "c.name as categoryName" +
            " from products p join brands b on p.brand_id = b.id" +
            " join categories c on p.category_id = c.id where p.id = :id", nativeQuery = true)
    Optional<ProductProjectionById> getProductById(int id);


    @Query(value = "select id from products where name = :name", nativeQuery = true)
    Integer checkToUnique(String name);

}
