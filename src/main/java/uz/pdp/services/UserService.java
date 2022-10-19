package uz.pdp.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.dtos.UserDto;
import uz.pdp.entities.ImageData;
import uz.pdp.entities.Role;
import uz.pdp.entities.User;
import uz.pdp.projections.UserProjection;
import uz.pdp.repositories.RoleRepo;
import uz.pdp.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uz.pdp.util.RoleEnum;

import java.io.File;
import java.util.*;

import static uz.pdp.util.Util.UPLOAD_DIRECTORY;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepo  userRepo;
    private final ImageService imageService;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepo roleRepo;

    public void save(UserDto userDto, MultipartFile image) {
        Role roleUser = roleRepo.findByName(RoleEnum.ROLE_USER.name());
        Set<Role> role = new HashSet<>(Collections.singletonList(roleUser));
        User user = User
                .builder()
                .fullName(userDto.getFullName())
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .roles(role)
                .build();
        ImageData save = imageService.save(image);
        user.setImage(save);
        userRepo.save(user);

    }

    public Page<UserProjection> getAllUsers(int page, int size) {
        return userRepo.getUsers(PageRequest.of(page-1,size));
    }

    public boolean delete(int id) {
        try {
            userRepo.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public UserProjection getUserById(int id) {
        Optional<UserProjection> userById = userRepo.getUserById(id);
        return userById.get();
    }

    public boolean checkToUnique(String phoneNumber) {
        return userRepo.checkToUnique(phoneNumber)!=null;
    }

    public void edit(UserDto userDto, MultipartFile image) {
        List<Role> allById = roleRepo.findAllById(userDto.getRolesId());
        Set<Role> roles = new HashSet<>(allById);
        User user = User.builder()
                .fullName(userDto.getFullName())
                .username(userDto.getUsername())
                .roles(roles)
                .password(passwordEncoder.encode(userDto.getPassword()))
                .build();
        user.setId(userDto.getId());
        Optional<UserProjection> userById = userRepo.getUserById(user.getId());
        File file = new File(UPLOAD_DIRECTORY + userById.get().getPhotoName());
        file.delete();
        imageService.save(image);
        userRepo.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepo.findByUsername(username);
        if (user.isEmpty()) throw new RuntimeException("User not found");
        return user.get();
    }
}

