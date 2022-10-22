package uz.pdp.dtos;

import uz.pdp.validations.PasswordsMatch;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@PasswordsMatch(message = "Passwords don't match")
public class UserDto {

    private Integer id;

    @NotBlank
    private String fullName;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String  password;

    @NotBlank
    private String confirmPassword;

    private List<Integer> rolesId;

    private List<Integer> permissionsId;
}
