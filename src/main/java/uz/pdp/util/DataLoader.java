package uz.pdp.util;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.entities.ImageData;
import uz.pdp.entities.Permission;
import uz.pdp.entities.Role;
import uz.pdp.entities.User;
import uz.pdp.repositories.PermissionRepo;
import uz.pdp.repositories.RoleRepo;
import uz.pdp.repositories.UserRepo;
import uz.pdp.services.ImageService;
import uz.pdp.services.UserService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    @Value("${spring.sql.init.mode}")
    private String initMode;

    private final RoleRepo roleRepo;

    private final UserRepo userRepo;


    private final PasswordEncoder passwordEncoder;
    private final ImageService imageService;
    private final PermissionRepo permissionRepo;


    @Override
    public void run(String... args) throws Exception {

        if (initMode.equals("always")) {
            Role roleSuperAdmin = roleRepo.save(Role.builder()
                    .name(RoleEnum.ROLE_SUPER_ADMIN)
                    .build());
            roleRepo.save(Role.builder()
                    .name(RoleEnum.ROLE_ADMIN)
                    .build());
            roleRepo.save(Role.builder()
                    .name(RoleEnum.ROLE_USER)
                    .build());

            permissionRepo.save(Permission.builder()
                    .name(PermissionEnum.CAN_DELETE_CATEGORY)
                    .build());
            permissionRepo.save(Permission.builder()
                    .name(PermissionEnum.CAN_DELETE_BRAND)
                    .build());
            permissionRepo.save(Permission.builder()
                    .name(PermissionEnum.CAN_DELETE_CH_VALUE)
                    .build());
            permissionRepo.save(Permission.builder()
                    .name(PermissionEnum.CAN_CHANGE_ORDER_STATUS)
                    .build());
            permissionRepo.save(Permission.builder()
                    .name(PermissionEnum.CAN_DELETE_USER)
                    .build());
            permissionRepo.save(Permission.builder()
                    .name(PermissionEnum.CAN_DELETE_CHARACTERISTIC)
                    .build());
            permissionRepo.save(Permission.builder()
                    .name(PermissionEnum.CAN_DELETE_PRODUCT)
                    .build());

            Set<Role> roles = new HashSet<>();
            roles.add(roleSuperAdmin);

            Path path = Paths.get("src/main/resources/image/user.png");
            MultipartFile defaultImage = new MockMultipartFile("user.png", "user.png",
                    "image/png", Files.readAllBytes(path));
            ImageData save = imageService.save(defaultImage, "SUPER_ADMIN");
            User user = new User("Oybek Akhmadjonov", "oybekakhmadjonov1@gmail.com", passwordEncoder.encode("1120"), true, save, roles, null);
            userRepo.save(user);
        }
    }
}
