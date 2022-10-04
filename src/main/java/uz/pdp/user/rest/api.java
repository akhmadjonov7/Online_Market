package uz.pdp.user.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class api {
    private String message;
    private Boolean isSuccess;
    private Object Date;
}
