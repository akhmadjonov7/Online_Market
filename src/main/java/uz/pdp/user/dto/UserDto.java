package uz.pdp.user.dto;

import com.example.demo.user.validation.PasswordsMatch;
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


    private Integer id;

    @NotBlank
    private String full_name;

    @NotBlank
    private String username;

//    @NotBlank
    private Integer password;

//    @NotBlank
    private Integer confirmPassword;

}
