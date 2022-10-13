//package uz.pdp.controllers;
//
//import uz.pdp.dtos.UserDto;
//import uz.pdp.entities.User;
//import uz.pdp.util.Api;
//import uz.pdp.services.UserService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//
//@RequiredArgsConstructor
//@RestController
//@RequestMapping("/users")
//public class UserCtrl {
//
//    private final UserService userService;
//
//    @PostMapping("/add")
//    public HttpEntity<?> save(@RequestBody @Valid  UserDto userDto, BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            return ResponseEntity.ok(new Api("", false, bindingResult.getAllErrors()));
//        }
//        User user = User
//                .builder()
//                .id(userDto.getId())
//                .full_name(userDto.getFull_name())
//                .username(userDto.getUsername())
//                .password(userDto.getPassword())
//                .build();
//        userService.save(user);
//        return ResponseEntity.ok(new Api("", true, null));
//    }
//
//    @GetMapping
//    public HttpEntity<?> getAllUsers(@RequestParam(name = "page",defaultValue = "1") int page,
//                                     @RequestParam(name = "size",defaultValue = "5") int size) {
//        Page<User> userList = userService.getAllUsers(page,size);
//        return ResponseEntity.ok(new Api("", true, userList));
//    }
//
//    @GetMapping("/delete/{id}")
//    public HttpEntity<?> delete(@PathVariable Integer id) {
//        boolean delete = userService.delete(id);
//        if (delete) {
//            return ResponseEntity.ok(new Api("", true, null));
//        }
//        return ResponseEntity.ok(new Api("Error", false, null));
//    }
//
//    @GetMapping("/{id}")
//    public HttpEntity<?> getUserById(@PathVariable Integer id) {
//        User userById = userService.getUserById(id);
//        if (userById == null) {
//            return ResponseEntity.ok(new Api("Not Found", false, null));
//        }
//        return ResponseEntity.ok(new Api("", true, userById));
//    }
//}
