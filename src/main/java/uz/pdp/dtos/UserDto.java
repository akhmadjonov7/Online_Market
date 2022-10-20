package uz.pdp.dtos;

import uz.pdp.util.PermissionEnum;
import uz.pdp.util.RoleEnum;
import uz.pdp.validations.PasswordsMatch;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@PasswordsMatch(message = "Passwords don't match")
public class UserDto {

//    @NotBlank
    private Integer id;

    @NotBlank
    private String fullName;

    @NotBlank
    @Email
    private String username;

    @NotBlank
    private String  password;

    @NotBlank
    private String confirmPassword;

//    @NotNull
    private List<Integer> rolesId;
//    @NotNull
    private List<Integer> permissionsId;
}
