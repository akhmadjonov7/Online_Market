package uz.pdp.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.pdp.services.ImageService;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import static uz.pdp.util.UploadDirectory.UPLOAD_DIRECTORY;

@Controller
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageCtrl {
    private final ImageService imageService;
    @GetMapping("/{image_name}")
    public void showImage(@PathVariable String image_name, HttpServletResponse res){
        if (image_name==null) {
            return;
        }
        res.setHeader("Content-Disposition", "attachment; filename=" + image_name);
        res.setHeader("Content-Transfer-Encoding", "binary");
        try {
            BufferedOutputStream outputStream = new BufferedOutputStream(res.getOutputStream());
            FileInputStream inputStream = new FileInputStream(UPLOAD_DIRECTORY + image_name);
            int len;
            byte[] bytes = new byte[1024];
            while ((len = inputStream.read(bytes)) > 0) {
                outputStream.write(bytes, 0, len);
            }
            outputStream.close();
            res.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
