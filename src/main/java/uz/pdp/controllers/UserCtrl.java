package uz.pdp.controllers;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.dtos.UserDto;
import uz.pdp.entities.Role;
import uz.pdp.entities.User;
import uz.pdp.projections.UserProjection;
import uz.pdp.repositories.RoleRepo;
import uz.pdp.util.ApiResponse;
import uz.pdp.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uz.pdp.util.RoleEnum;

import javax.validation.Valid;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;
import static java.util.Arrays.stream;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserCtrl {

    private final UserService userService;
    private final RoleRepo roleRepo;

    private final PasswordEncoder passwordEncoder;
    @PostMapping("/register")
    public HttpEntity<?> save(@Valid @RequestPart UserDto userDto, BindingResult bindingResult, @RequestPart(required = false) MultipartFile image) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.ok(new ApiResponse("", false, bindingResult.getAllErrors()));
        }
        if (userService.checkToUnique(userDto.getUsername())) {
            return ResponseEntity.badRequest().body(new ApiResponse("This phone number has already exists", false, null));
        }
        userService.save(userDto, image);
        return ResponseEntity.ok(new ApiResponse("", true, null));
    }

    @GetMapping
    public HttpEntity<?> getAllUsers(@RequestParam(name = "page", defaultValue = "1") int page,
                                     @RequestParam(name = "size", defaultValue = "5") int size) {
        if (page <= 0) page = 1;
        if (size <= 0) size = 5;
        Page<UserProjection> userList = userService.getAllUsers(page, size);
        return ResponseEntity.ok(new ApiResponse("", true, userList));
    }

    @GetMapping("/delete/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id) {
        boolean delete = userService.delete(id);
        if (delete) {
            return ResponseEntity.ok(new ApiResponse("", true, null));
        }
        return ResponseEntity.badRequest().body(new ApiResponse("Error", false, null));
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getUserById(@PathVariable Integer id) {
        UserProjection userById = userService.getUserById(id);
        if (userById == null) {
            return ResponseEntity.ok(new ApiResponse("Not Found", false, null));
        }
        return ResponseEntity.ok(new ApiResponse("", true, userById));
    }

    @PutMapping
    public HttpEntity<?> updateUser(@Valid @RequestBody UserDto userDto, BindingResult bindingResult, @RequestPart MultipartFile image) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new ApiResponse("Validation", false, bindingResult.getAllErrors()));
        }
        if (userService.checkToUnique(userDto.getUsername())) {
            return ResponseEntity.badRequest().body(new ApiResponse("This phone number has already exists", false, null));
        }
        if (userDto.getId() == null) {
            return ResponseEntity.badRequest().body(new ApiResponse("Something wrong!!!", false, null));
        }

        try {
            userService.edit(userDto, image);
            return ResponseEntity.ok(new ApiResponse("", true, null));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse("User not found", false, null));
        }
    }
}
