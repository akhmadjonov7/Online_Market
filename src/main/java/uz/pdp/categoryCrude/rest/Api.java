package uz.pdp.categoryCrude.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor @NoArgsConstructor @Data
@Builder
public class Api {
    private String  message;
    private Boolean status;
    private Object Date;
}
