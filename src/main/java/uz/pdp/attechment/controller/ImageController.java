package uz.pdp.attechment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.pdp.attechment.model.ImageDataDto;
import uz.pdp.attechment.service.ImageService;

@Controller
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;
    @GetMapping("/{image_name}")
    public HttpEntity<?> showImage(@PathVariable String image_name){
        ImageDataDto image = imageService.getImage(image_name);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + image.getPhotoName() + "\"")
                .contentType(MediaType.parseMediaType(image.getContentType())).body(image.getData());
    }
}
