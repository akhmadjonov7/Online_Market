package uz.pdp.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.entities.ImageData;
import uz.pdp.repositories.ImageRepo;
import uz.pdp.repositories.ProductRepo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static uz.pdp.util.Util.UPLOAD_DIRECTORY;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepo imageRepo;
    private final ProductRepo productRepo;

    public ImageData save(MultipartFile image, String name) {
        ImageData imageData = new ImageData();
        File uploadDir = new File(UPLOAD_DIRECTORY);
        if (!uploadDir.exists())
            uploadDir.mkdirs();
        int index = image.getOriginalFilename().lastIndexOf('.');
        String extension = image.getOriginalFilename().substring(index + 1);
        String imgName = name + "." + extension;
        String imgPath = uploadDir.getPath() + "/" + imgName;
        try {
            image.transferTo(Path.of(imgPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        imageData.setPhotoName(imgName);
        return imageRepo.save(imageData);
    }


    public List<ImageData> saveAll(List<MultipartFile> imageList, String name) {
        List<ImageData> imageDataList = new ArrayList<>();
        for (int i = 0; i < imageList.size(); i++)
         {
             MultipartFile image = imageList.get(i);
             ImageData imageData = new ImageData();
            File uploadDir = new File(UPLOAD_DIRECTORY);
            if (!uploadDir.exists())
                uploadDir.mkdirs();
            int index = image.getOriginalFilename().lastIndexOf('.');
            String extension = image.getOriginalFilename().substring(index + 1);
            String imgName = name + "(" + (i+1) + ")." + extension;
            String imgPath = uploadDir.getPath() + "/" + imgName;
            try {
                image.transferTo(Path.of(imgPath));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            imageData.setPhotoName(imgName);
            ImageData save = imageRepo.save(imageData);
            imageDataList.add(save);
        }
        return imageDataList;
    }

}
