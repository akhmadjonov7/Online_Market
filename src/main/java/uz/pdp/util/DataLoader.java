package uz.pdp.util;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.dtos.UserDto;
import uz.pdp.entities.ImageData;
import uz.pdp.entities.Role;
import uz.pdp.entities.User;
import uz.pdp.repositories.RoleRepo;
import uz.pdp.repositories.UserRepo;
import uz.pdp.services.ImageService;
import uz.pdp.services.UserService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    @Value("${spring.sql.init.mode}")
    private String initMode;

    private final RoleRepo roleRepo;

    private final UserRepo userRepo;

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;
    private final ImageService imageService;


    @Override
    public void run(String... args) throws Exception {

        if (initMode.equals("always")) {
            Role roleSuperAdmin = roleRepo.save(Role.builder()
                    .name(RoleEnum.ROLE_SUPER_ADMIN)
                    .build());
            Role roleAdmin = roleRepo.save(Role.builder()
                    .name(RoleEnum.ROLE_ADMIN)
                    .build());
            Role roleUser = roleRepo.save(Role.builder()
                    .name(RoleEnum.ROLE_USER)
                    .build());

            Set<Role> roles = new HashSet<>();
            roles.add(roleSuperAdmin);
            roles.add(roleAdmin);
            roles.add(roleUser);
//            UserDto userDto = UserDto.builder().fullName("Oybek Akhmadjonov").username("akhmadjonov").password("1120").confirmPassword("1120")
//                    .rolesId(List.of(1, 2, 3)).build();

            Path path = Paths.get("src/main/resources/image/user.png");
            MultipartFile defaultImage = new MockMultipartFile("user.png", "user.png",
                    "image/png", Files.readAllBytes(path));
            ImageData save = imageService.save(defaultImage);
            User user = new User("Oybek Akhmadjonov","akhmadjonov",passwordEncoder.encode("1120"),true,save,roles,null);
//            userService.save(userDto,null);
            userRepo.save(user);
        }
    }
}
