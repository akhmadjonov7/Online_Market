package uz.pdp.attechment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.attechment.model.ImageData;
import uz.pdp.attechment.model.ImageDataDto;
import uz.pdp.attechment.projection.ImageDataProjection;
import uz.pdp.attechment.repository.ImageRepo;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import static uz.pdp.UploadDirectory.UPLOAD_DIRECTORY;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepo imageRepo;
    public ImageData save(MultipartFile image){
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
        return save;
    }
    public ImageDataDto getImage(String  image){
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
}
