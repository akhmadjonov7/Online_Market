package uz.pdp.services;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.dtos.CharacteristicChValueDto;
import uz.pdp.dtos.ProductDto;
import uz.pdp.entities.*;
import uz.pdp.projections.ImageDataProjection;
import uz.pdp.projections.ProductProjection;
import uz.pdp.projections.ProductProjectionById;
import uz.pdp.repositories.CharactreristicChValueRepo;
import uz.pdp.repositories.ImageRepo;
import uz.pdp.repositories.ProductRepo;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static uz.pdp.util.UploadDirectory.UPLOAD_DIRECTORY;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepo productRepo;
    private final ImageRepo imageRepo;
    private final ImageService imageService;
    private final CharactreristicChValueRepo charactreristicChValueRepo;
    @SneakyThrows
    public Product save(ProductDto productDto, List<MultipartFile> imageList){
        Brand brand = new Brand();
        Category category = new Category();
        category.setId(productDto.getCategoryId());
        brand.setId(productDto.getBrandId());
        Product product = Product.builder()
                .price(productDto.getPrice())
                .amount(productDto.getAmount())
                .brand(brand)
                .category(category)
                .build();
        product.setName(productDto.getName());
        if (imageList.size() == 0) {
            Path path = Paths.get("src/main/resources/image/download.png");
            MultipartFile defaultImage = new MockMultipartFile("download.png", "download.png",
                    "image/png", Files.readAllBytes(path));
            ImageData save = imageService.save(defaultImage);
            product.setImage((List.of(save)));
            product.setMainImage(save);
        } else {
            List<ImageData> save = imageService.saveAll(imageList);
            product.setMainImage(save.get(0));
            product.setImage(save);
        }
        List<CharacteristicsChValues> characteristicsChValues = new ArrayList<>();
        for ( CharacteristicChValueDto chValueDto : productDto.getCharacteristicsChValues()) {
            Integer characteristicId = charactreristicChValueRepo.getCharacteristicId(chValueDto.getCharacteristicId(), chValueDto.getCharacteristicValueId());
            CharacteristicsChValues chValues = new CharacteristicsChValues();
            chValues.setId(characteristicId);
            characteristicsChValues.add(chValues);
        }
        product.setCharacteristicsChValues(characteristicsChValues);
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
        Optional<ProductProjectionById> productOptional = productRepo.getProductById(id);
        if (productOptional.isEmpty()) {
            return null;
        }
        ProductProjectionById productProjection = productOptional.get();
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
        List<ImageDataProjection> productImages = imageRepo.getProductImages(id);
        productDto.setImages(productImages);
        return productDto;
    }

    public Page<ProductProjection> showProducts(int page, int size) {
        Page<ProductProjection> products = productRepo.getProducts(PageRequest.of(page-1,size));
        return products;
    }
}
