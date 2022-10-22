package uz.pdp.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uz.pdp.entities.Characteristic;
import uz.pdp.projections.CharacteristicProjection;
import uz.pdp.services.CharacteristicService;
import uz.pdp.util.ApiResponse;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/characteristics")
public class CharacteristicCtrl {
    private final CharacteristicService characteristicService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN' , 'ROLE_SUPER_ADMIN')")
    public HttpEntity<?> addCharacteristic(@Valid @RequestBody Characteristic characteristic, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return ResponseEntity.badRequest().body(new ApiResponse("Validation", false, bindingResult.getAllErrors()));
        if (characteristicService.checkToUnique(characteristic.getName()))
            return ResponseEntity.badRequest().body(new ApiResponse("Error", false, "This characteristic has already exists!!!"));
        characteristicService.save(characteristic);
        return ResponseEntity.ok(new ApiResponse("Added!!!",true,null));
    }

    @GetMapping("/edit")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN' , 'ROLE_SUPER_ADMIN')")
    public HttpEntity<?> getAllCharactersitics(@RequestParam(name = "size", defaultValue = "5") int size, @RequestParam(name = "page", defaultValue = "1") int page) {
        if (page <= 0) page = 1;
        if (size <= 0) size = 5;
        Page<CharacteristicProjection> allCharactersitic = characteristicService.getAllCharactersitic(size, page);
        return ResponseEntity.ok(new ApiResponse("", true, allCharactersitic));
    }

    @GetMapping
    public HttpEntity<?> getAllCharactersiticsForChoose() {
        List<CharacteristicProjection> allCharactersitic = characteristicService.getAllCharactersiticForChoose();
        return ResponseEntity.ok(new ApiResponse("", true, allCharactersitic));
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN' , 'ROLE_SUPER_ADMIN')")
    public HttpEntity<?> editCharacteristic(@Valid @RequestBody Characteristic characteristic, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return ResponseEntity.ok(bindingResult.getAllErrors());
        if (characteristicService.checkToUnique(characteristic.getName()))
            return ResponseEntity.badRequest().body(new ApiResponse("Error", false, "This characteristic has already exists!!!"));
        try {
            characteristicService.save(characteristic);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse("Not Found", false, null));
        }
        return ResponseEntity.ok(new ApiResponse("Edited", true, null));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('CAN_DELETE_CHARACTERISTIC' , 'ROLE_SUPER_ADMIN')")

    public HttpEntity<?> deleteCharacteristic(@PathVariable Integer id) {
        boolean delete = characteristicService.delete(id);
        if (delete) {
            return ResponseEntity.ok(new ApiResponse("Deleted",true,null));
        }
        return ResponseEntity.badRequest().body(new ApiResponse("You cannot delete this characteristic", false, null));
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getCharacteristicById(@PathVariable Integer id) {
        Characteristic characteristicById = characteristicService.getCharacteristicById(id);
        if (characteristicById == null) {
            return ResponseEntity.badRequest().body(new ApiResponse("Not Found",false,null));
        }
        return ResponseEntity.ok(new ApiResponse("", true, characteristicById));
    }
}
