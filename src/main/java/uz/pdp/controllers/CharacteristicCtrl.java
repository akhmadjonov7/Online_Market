package uz.pdp.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uz.pdp.dtos.CharacteristicDto;
import uz.pdp.entities.Characteristic;
import uz.pdp.projections.CharacteristicProjection;
import uz.pdp.services.CharacteristicService;
import uz.pdp.util.Api;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/characteristics")
public class CharacteristicCtrl {
    private final CharacteristicService characteristicService;
    @PostMapping
    public HttpEntity<?> addCharacteristic(@Valid @RequestBody CharacteristicDto characteristicDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return ResponseEntity.ok(new Api("", false, bindingResult.getAllErrors()));
        }
        characteristicService.save(characteristicDto);
        return ResponseEntity.ok("Added!!!");
    }
    @GetMapping("/edit")
    public HttpEntity<?> getAllCharactersitics(@RequestParam(name = "size",defaultValue = "5") int size, @RequestParam(name = "page", defaultValue = "1") int page){
        Page<CharacteristicProjection> allCharactersitic = characteristicService.getAllCharactersitic(size, page);
        if (allCharactersitic.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(new Api("",true, allCharactersitic));
    }
    @GetMapping
    public HttpEntity<?> getAllCharactersiticsForChoose(){
        List<CharacteristicProjection> allCharactersitic = characteristicService.getAllCharactersiticForChoose();
        if (allCharactersitic.size()==0) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(new Api("",true, allCharactersitic));
    }
    @PutMapping
    public HttpEntity<?> editCharacteristic(@Valid @RequestBody CharacteristicDto characteristicDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return ResponseEntity.ok(bindingResult.getAllErrors());
        }
        try {
            characteristicService.save(characteristicDto);
        } catch (Exception e) {
            return ResponseEntity.ok(new Api("Not Found",false,null));
        }
        return ResponseEntity.ok(new Api("",true,null));
    }
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteCharacteristic(@PathVariable Integer id){
        boolean delete = characteristicService.delete(id);
        if (delete) {
            return ResponseEntity.ok("Deleted");
        }
        return ResponseEntity.ok(new Api("You cannot delete this characteristic",false , null));
    }
    @GetMapping("/{id}")
    public HttpEntity<?> getCharacteristicById(@PathVariable Integer id){
        Characteristic characteristicById = characteristicService.getCharacteristicById(id);
        if (characteristicById==null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new Api("",true,characteristicById));
    }
}
