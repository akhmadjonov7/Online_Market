package uz.pdp.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.dtos.UserDto;
import uz.pdp.dtos.UserRoleDto;
import uz.pdp.entities.User;
import uz.pdp.projections.UserProjection;
import uz.pdp.repositories.RoleRepo;
import uz.pdp.repositories.UserRepo;
import uz.pdp.util.ApiResponse;
import uz.pdp.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static uz.pdp.util.Util.algorithm;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserCtrl {

    private final UserService userService;
    private final UserRepo userRepo;


    @SneakyThrows
    @PostMapping("/register")
    public HttpEntity<?> save(@Valid @RequestPart UserDto userDto, BindingResult bindingResult, @RequestPart(required = false) MultipartFile image) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.ok(new ApiResponse("", false, bindingResult.getAllErrors()));
        }
        if (userService.checkToUnique(userDto.getEmail())) {
            return ResponseEntity.badRequest().body(new ApiResponse("This email has already exists", false, null));
        }
        userService.save(userDto, image);
        return ResponseEntity.ok(new ApiResponse("Verify your email", true, null));
    }

    @GetMapping("/refresh")
    @SneakyThrows
    public void refresh(HttpServletResponse response, HttpServletRequest request) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refreshToken = authorizationHeader.substring(7);
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refreshToken);
                String email = decodedJWT.getSubject();
                User user = userRepo.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
                String accessToken = JWT.create()
                        .withSubject(user.getEmail())
                        .withClaim("userId", user.getId())
                        .withExpiresAt(new Date(System.currentTimeMillis() + (1000 * 60 * 10)))
                        .withClaim("roles", user.getRoles().stream().map(role -> role.getName().name()).collect(Collectors.toList()))
                        .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", accessToken);
                tokens.put("refresh_token", refreshToken);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception e) {
                response.setStatus(403);
                Map<String, String> error = new HashMap<>();
                error.put("error_message", e.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        }
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    public HttpEntity<?> getAllUsers(@RequestParam(name = "page", defaultValue = "1") int page,
                                     @RequestParam(name = "size", defaultValue = "5") int size) {
        if (page <= 0) page = 1;
        if (size <= 0) size = 5;
        Page<UserProjection> userList = userService.getAllUsers(page, size);
        return ResponseEntity.ok(new ApiResponse("", true, userList));
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_SUPER_ADMIN','CAN_DELETE_USER')")
    public HttpEntity<?> delete(@PathVariable Integer id) {
        boolean delete = userService.delete(id);
        if (delete) {
            return ResponseEntity.ok(new ApiResponse("", true, null));
        }
        return ResponseEntity.badRequest().body(new ApiResponse("Error", false, null));
    }

    @GetMapping("/me")
    public HttpEntity<?> getInfoByMe(@AuthenticationPrincipal User currentUser) {
        currentUser.setPassword(null);
        return ResponseEntity.ok(new ApiResponse("", true, currentUser));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SUPER_ADMIN')")
    public HttpEntity<?> getUserById(@PathVariable Integer id) {
        if (id == null) {
            return ResponseEntity.badRequest().body(new ApiResponse("Id is null", false, null));
        }
        UserProjection userById = userService.getUserById(id);
        return ResponseEntity.ok(new ApiResponse("", true, userById));
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('USER')")
    public HttpEntity<?> updateUser(@Valid @RequestPart UserDto userDto, BindingResult bindingResult, @RequestPart(required = false) MultipartFile image, @AuthenticationPrincipal User currentUser) {
        if (userDto.getId() != currentUser.getId())
            return ResponseEntity.badRequest().body(new ApiResponse("Somethign wrong!!!", false, null));
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new ApiResponse("Validation", false, bindingResult.getAllErrors()));
        }
        if (userDto.getId() == null) {
            return ResponseEntity.badRequest().body(new ApiResponse("Something wrong!!!", false, null));
        }

        return userService.edit(userDto, image, currentUser);
    }

    @GetMapping("/verify/{id}")
    public HttpEntity<?> verify(@PathVariable Integer id) {
        if (id == null) {
            return ResponseEntity.badRequest().body(new ApiResponse("Id is null", false, null));
        }
        boolean verify = userService.verify(id);
        if (verify) {
            return ResponseEntity.ok(new ApiResponse("Accaunt successfully verified", true, false));
        }
        return ResponseEntity.badRequest().body(new ApiResponse("User not found", false, null));
    }

    @PutMapping("/addrole")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
    public HttpEntity<?> addRoleToUser(@RequestBody UserRoleDto userRoleDto){
        return userService.changeRoleOfUser(userRoleDto);
    }
    @PutMapping("/addpermission")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
    public HttpEntity<?> addPermissionToUser(@RequestBody UserRoleDto userRoleDto){
        return userService.changePermissionOfUser(userRoleDto);
    }

}
