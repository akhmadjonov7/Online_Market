package uz.pdp.user.controller;

import com.example.demo.user.dto.UserDto;
import com.example.demo.user.model.User;
import com.example.demo.user.rest.api;
import com.example.demo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/add")
    public HttpEntity<?> save(@RequestBody @Valid  UserDto userDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.ok(new api("", false, bindingResult.getAllErrors()));
        }
        User user = User
                .builder()
                .id(userDto.getId())
                .full_name(userDto.getFull_name())
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .build();
        userService.save(user);
        return ResponseEntity.ok(new api("", true, null));
    }

    @GetMapping
    public HttpEntity<?> getAllUsers(@RequestParam(name = "page",defaultValue = "1") int page, @RequestParam(name = "size",defaultValue = "5") int size) {
        Page<User> userList = userService.getAllUsers(page,size);
        return ResponseEntity.ok(new api("", true, userList));
    }

    @GetMapping("/delete/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id) {
        boolean delete = userService.delete(id);
        if (delete) {
            return ResponseEntity.ok(new api("", true, null));
        }
        return ResponseEntity.ok(new api("Error", false, null));
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getUserById(@PathVariable Integer id) {
        User userById = userService.getUserById(id);
        if (userById == null) {
            return ResponseEntity.ok(new api("Not Found", false, null));
        }
        return ResponseEntity.ok(new api("", true, userById));
    }
}
