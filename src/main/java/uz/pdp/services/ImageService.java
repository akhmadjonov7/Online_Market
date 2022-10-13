package uz.pdp.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.entities.ImageData;
import uz.pdp.dtos.ImageDataDto;
import uz.pdp.projections.ImageDataProjection;
import uz.pdp.repositories.ImageRepo;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static uz.pdp.util.UploadDirectory.UPLOAD_DIRECTORY;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepo imageRepo;

    public ImageData save(MultipartFile image) {
        ImageData imageData = new ImageData();
        imageData.setContentType(image.getContentType());
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
        imageData.setPhotoName(imgName);
        return imageRepo.save(imageData);
    }

    public ImageDataDto getImage(String image) {
        File file = new File(UPLOAD_DIRECTORY + image);
        try {
            BufferedImage read = ImageIO.read(file);
            WritableRaster raster = read.getRaster();
            DataBufferByte dataBuffer = (DataBufferByte) raster.getDataBuffer();
            ImageDataProjection imageDataProjection = imageRepo.showImageDataByPhotoName(image);
            ImageDataDto imageDataDto = ImageDataDto.builder()
                    .id(imageDataProjection.getId())
                    .photoName(imageDataProjection.getPhotoName())
                    .contentType(imageDataProjection.getContentType())
                    .data(dataBuffer.getData())
                    .build();
            return imageDataDto;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ImageData> saveAll(List<MultipartFile> imageList) {
        List<ImageData> imageDataList = new ArrayList<>();
        for (MultipartFile image : imageList) {
            ImageData imageData = new ImageData();
            imageData.setContentType(image.getContentType());
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
            imageData.setPhotoName(imgName);
            ImageData save = imageRepo.save(imageData);
            imageDataList.add(save);
        }
        return imageDataList;
    }
}
