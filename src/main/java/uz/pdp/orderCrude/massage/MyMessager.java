package uz.pdp.orderCrude.massage;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MyMessager {

    private String message;

    private boolean status;


    private Object Date ;


}
