package uz.pdp.util;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.pdp.entities.Role;
import uz.pdp.entities.User;
import uz.pdp.repositories.RoleRepo;
import uz.pdp.repositories.UserRepo;

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


    @Override
    public void run(String... args) throws Exception {

        if (initMode.equals("always")) {
            Role save = roleRepo.save(Role.builder()
                    .name(RoleEnum.ROLE_SUPER_ADMIN)
                    .build());
            roleRepo.save(Role.builder()
                    .name(RoleEnum.ROLE_ADMIN)
                    .build());
            roleRepo.save(Role.builder()
                    .name(RoleEnum.ROLE_USER)
                    .build());

            Set<Role> roles = new HashSet<>();
            roles.add(save);
            userRepo.save(User.builder()
                            .username("akhmadjonov")
                            .password(passwordEncoder.encode("1120"))
                            .fullName("Oybek Akhmadjonov")
                            .isEnabled(true)
                            .roles(roles)
                    .build());

        }
    }
}
