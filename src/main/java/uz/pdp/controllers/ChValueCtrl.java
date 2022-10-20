package uz.pdp.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uz.pdp.dtos.ChValueDto;
import uz.pdp.entities.CharacteristicValue;
import uz.pdp.services.ChValueService;
import uz.pdp.util.ApiResponse;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/chvalues")
public class ChValueCtrl {
    private final ChValueService chValueService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN' or 'ROLE_SUPER_ADMIN')")
    public HttpEntity<?> addChValues(@Valid @RequestBody List<ChValueDto> chValueDtos, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
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
            return ResponseEntity.ok(new ApiResponse("Added!!!",true,null));
        }
        return ResponseEntity.ok(new ApiResponse("This values has already had", true, cannotAdd));
    }

    @GetMapping("/edit")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN' or 'ROLE_SUPER_ADMIN')")
    public HttpEntity<?> getAllValuesForEdit(@RequestParam(name = "size", defaultValue = "5") int size, @RequestParam(name = "page", defaultValue = "1") int page) {
        if (page<=0) page = 1;
        if (size<=0) size = 5;
        Page<CharacteristicValue> allChValues = chValueService.getAllChValues(size, page);
        if (allChValues.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse("",true,null));
        }
        return ResponseEntity.ok(new ApiResponse("", true, allChValues));
    }

    @GetMapping("/{characteristicId}")
    public HttpEntity<?> getAllValuesByCharacteristicId(@PathVariable Integer characteristicId) {
        List<String> chValuesByCharacteristicId = chValueService.getChValuesByCharacteristicId(characteristicId);
        return ResponseEntity.ok(new ApiResponse("", true, chValuesByCharacteristicId));
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN' or 'ROLE_SUPER_ADMIN')")

    public HttpEntity<?> editChValue(@Valid @RequestBody ChValueDto chValueDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        boolean update = chValueService.update(chValueDto);
        if (update) {
            return ResponseEntity.ok(new ApiResponse("Edited",true,null));
        }
        return ResponseEntity.badRequest().body(new ApiResponse("This value not found", false, null));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('CAN_DELETE_CH_VALUE' or 'ROLE_SUPER_ADMIN')")

    public HttpEntity<?> deleteChValue(@PathVariable Integer id){
        boolean delete = chValueService.delete(id);
        if (delete) {
            return ResponseEntity.ok(new ApiResponse("Deleted!!!",true,null));
        }
        return ResponseEntity.badRequest().body(new ApiResponse("You cannot delete this value!!!", false, null));
    }
}
