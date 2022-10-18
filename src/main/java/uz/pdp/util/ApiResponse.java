package uz.pdp.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor @NoArgsConstructor @Data
@Builder
public class ApiResponse {
    private String  message;
    private Boolean status;
    private Object Data;
}
