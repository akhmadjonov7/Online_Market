package uz.pdp.dtos;

import uz.pdp.validations.PasswordsMatch;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@PasswordsMatch(message = "Passwords don't match")
public class UserDto {

    @NotBlank
    private Integer id;

    @NotBlank
    private String full_name;

    @NotBlank
    private String phone_number;

//    @NotBlank
    private Integer password;

//    @NotBlank
    private Integer confirmPassword;

}
