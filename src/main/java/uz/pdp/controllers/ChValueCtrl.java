package uz.pdp.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uz.pdp.dtos.ChValueDto;
import uz.pdp.entities.CharacteristicValue;
import uz.pdp.services.ChValueService;
import uz.pdp.util.Api;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chvalues")
public class ChValueCtrl {
    private final ChValueService chValueService;

    @PostMapping
    public HttpEntity<?> addChValues(@Valid @RequestBody List<ChValueDto> chValueDtos, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.ok(bindingResult.getAllErrors());
        }
        List<ChValueDto> cannotAdd = new ArrayList<>();
        for (ChValueDto chValueDto : chValueDtos) {
            try {
                chValueService.save(chValueDto);
            } catch (Exception e) {
                cannotAdd.add(chValueDto);
            }
        }
        if (cannotAdd.size() == 0) {
            return ResponseEntity.ok("Added!!!");
        }
        return ResponseEntity.ok(new Api("This values has already had", true, cannotAdd));
    }

    @GetMapping
    public HttpEntity<?> getAllValuesForEdit(@RequestParam(name = "size", defaultValue = "5") int size, @RequestParam(name = "page", defaultValue = "1") int page) {
        Page<CharacteristicValue> allChValues = chValueService.getAllChValues(size, page);
        if (allChValues.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(new Api("", true, allChValues));
    }

    @GetMapping("/{characteristicId}")
    public HttpEntity<?> getAllValuesByCharacteristicId(@PathVariable Integer characteristicId) {
        List<String> chValuesByCharacteristicId = chValueService.getChValuesByCharacteristicId(characteristicId);
        return ResponseEntity.ok(new Api("", true, chValuesByCharacteristicId));
    }

    @PutMapping
    public HttpEntity<?> editChValue(@Valid @RequestBody ChValueDto chValueDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.ok(bindingResult.getAllErrors());
        }
        if (chValueDto.getId() == null) {
            return ResponseEntity.ok(new Api("you didn't give id", false, null));
        }
        boolean update = chValueService.update(chValueDto);
        if (update) {
            return ResponseEntity.ok("Updated!!!");
        }
        return ResponseEntity.ok(new Api("This value not found", false, null));
    }
}
