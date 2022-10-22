package uz.pdp.services;

import lombok.SneakyThrows;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.dtos.EmailDto;
import uz.pdp.dtos.UserDto;
import uz.pdp.dtos.UserRoleDto;
import uz.pdp.entities.ImageData;
import uz.pdp.entities.Permission;
import uz.pdp.entities.Role;
import uz.pdp.entities.User;
import uz.pdp.projections.UserProjection;
import uz.pdp.repositories.PermissionRepo;
import uz.pdp.repositories.RoleRepo;
import uz.pdp.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uz.pdp.util.ApiResponse;
import uz.pdp.util.PermissionEnum;
import uz.pdp.util.RoleEnum;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static uz.pdp.util.Util.UPLOAD_DIRECTORY;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepo  userRepo;
    private final ImageService imageService;
    private final PasswordEncoder passwordEncoder;

    private final PermissionRepo permissionRepo;
    private final RoleRepo roleRepo;
    private final EmailWithHtmlTemplate emailWithHtmlTemplate;

    public void save(UserDto userDto, MultipartFile image) throws IOException {
        Role role = roleRepo.findByName(RoleEnum.ROLE_USER);
        Set<Role> roles = new HashSet<>(List.of(role));
        User user = User
                .builder()
                .fullName(userDto.getFullName())
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .roles(roles)
                .build();
        saveImage(userDto, image, user);

        userRepo.save(user);
        EmailDto emailDto = new EmailDto(user.getEmail(),"Verify","Hi Verify you email",user.getFullName(), user.getId());
        new Thread(() -> emailWithHtmlTemplate.sendEmail(emailDto)).start();

    }

    private void saveImage(UserDto userDto, MultipartFile image, User user) throws IOException {
        if (image != null) {
            ImageData logo = imageService.save(image,userDto.getEmail());
            user.setImage(logo);
        } else {
            Path path = Paths.get("src/main/resources/image/user.png");
            MultipartFile defaultImage = new MockMultipartFile("user.png", "user.png",
                    "image/png", Files.readAllBytes(path));
            ImageData save = imageService.save(defaultImage,userDto.getEmail());
            user.setImage(save);
        }
    }

    public Page<UserProjection> getAllUsers(int page, int size) {
        return userRepo.getUsers(PageRequest.of(page-1,size));
    }

    public boolean delete(int id) {
        User user = userRepo.findById(id).orElseThrow(() -> new RuntimeException("User not Found"));
        user.setEnabled(false);
        try {
            userRepo.save(user);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public UserProjection getUserById(int id) {
        return userRepo.getUserById(id).orElseThrow(() -> new RuntimeException("User not Found") );
    }

    public boolean checkToUnique(String email) {
        return userRepo.checkToUnique(email)!=null;
    }

    @SneakyThrows
    public HttpEntity<ApiResponse> edit(UserDto userDto, MultipartFile image, User currentUser) {
        User user = userRepo.findById(currentUser.getId()).orElseThrow(() -> new RuntimeException("User not Found"));
        user.setId(userDto.getId());
        UserProjection userProjection = userRepo.getUserById(user.getId()).orElseThrow(() -> new RuntimeException("User not found"));
        new File(UPLOAD_DIRECTORY + userProjection.getPhotoName()).delete();
        saveImage(userDto, image, user);
        try {
            userRepo.save(user);
            return ResponseEntity.ok(new ApiResponse("Edited", true, null));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        if (!user.isEnabled()) throw new RuntimeException("Verify email");
        return user;
    }

    public boolean verify(Integer id) {
        try {
            userRepo.verify(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public HttpEntity<?> changeRoleOfUser(UserRoleDto userRoleDto) {
        User user = userRepo.findById(userRoleDto.getUserId()).orElseThrow(() -> new RuntimeException("User not Found"));
        Set<Role> roles = user.getRoles();
        user.getRoles().clear();
        userRepo.save(user);
        roles.add(roleRepo.findByName(RoleEnum.ROLE_USER));
        for (Integer roleId : userRoleDto.getRoleIds()) {
            RoleEnum role = RoleEnum.of(roleId);
            Role roleFromDb = roleRepo.findByName(role);
            roles.add(roleFromDb);
        }
        user.setRoles(roles);
        userRepo.save(user);
        return ResponseEntity.ok(new ApiResponse("Roles added!!!",true,null));
    }
    public HttpEntity<?> changePermissionOfUser(UserRoleDto userRoleDto) {
        User user = userRepo.findById(userRoleDto.getUserId()).orElseThrow(() -> new RuntimeException("User not Found"));
        Set<Permission> permissions = user.getPermissions();
        user.getPermissions().clear();
        userRepo.save(user);
        for (Integer permissionId : userRoleDto.getPermissionIds()) {
            PermissionEnum permission = PermissionEnum.of(permissionId);
            permissions.add(permissionRepo.findByName(permission));
        }
        user.setPermissions(permissions);
        userRepo.save(user);
        return ResponseEntity.ok(new ApiResponse("Permissions added!!!",true,null));
    }
}

