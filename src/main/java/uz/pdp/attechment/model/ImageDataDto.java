package uz.pdp.attechment.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ImageDataDto {
    private Integer id;

    private String photoName;

    private String contentType;

    private byte[] data;

}
