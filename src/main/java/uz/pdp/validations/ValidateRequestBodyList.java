package uz.pdp.validations;

import javax.validation.Valid;
import java.util.List;

public class ValidateRequestBodyList<T> {


    @Valid
    private List<@Valid T> requestBody;

    public List<T> getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(List<T> requestBody) {
        this.requestBody = requestBody;
    }

    public ValidateRequestBodyList(List<T> requestBody) {
        this.requestBody = requestBody;
    }

    public ValidateRequestBodyList() {
    }
}
