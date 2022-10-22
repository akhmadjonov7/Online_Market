package uz.pdp.controllers;


import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uz.pdp.dtos.CategoryDto;
import uz.pdp.entities.Category;
import uz.pdp.util.ApiResponse;
import uz.pdp.services.CategoryService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/categories")
public class CategoryCtrl {
    private final CategoryService categoryService;

    @GetMapping
    public HttpEntity<?> showCategories(@RequestParam (name = "page",defaultValue = "1") int page,
                                            @RequestParam(name = "size",defaultValue = "5") int size) {
        if (page<=0) page = 1;
        if (size<=0) size = 5;
        Page<Category> categoryPage = categoryService.allCategory(page, size);
        return ResponseEntity.ok(new ApiResponse("", true, categoryPage));
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN' , 'ROLE_SUPER_ADMIN')")
    public HttpEntity<?> addCategory(@Valid @RequestBody CategoryDto categoryDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return ResponseEntity.badRequest().body(new ApiResponse("Validation error",false,bindingResult.getAllErrors()));
        if (categoryService.checkToUnique(categoryDto.getName())) return ResponseEntity.badRequest().body(new ApiResponse("Error", false, "This category has already exists!!!"));
        categoryService.addCategory(categoryDto);
        return ResponseEntity.ok(new ApiResponse("", true, null));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('CAN_DELETE_CATEGORY' , 'ROLE_SUPER_ADMIN')")
    public HttpEntity<?> deleteCategory(@PathVariable int id) {
        boolean delete = categoryService.deleteCategory(id);
        if (delete) {
            return ResponseEntity.ok(new ApiResponse("", true, null));
        }
        return ResponseEntity.badRequest().body(new ApiResponse("You cannot delete this category",false,null));
    }


    @PutMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN' , 'ROLE_SUPER_ADMIN')")
    public HttpEntity<?> editCategory(@Valid @RequestBody CategoryDto categoryDto,BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return ResponseEntity.badRequest().body(new ApiResponse("Validation error",false,bindingResult.getAllErrors()));
        Category categoryServiceById = categoryService.getById(categoryDto.getId());
        if(!categoryServiceById.getName().equals(categoryDto.getName())) if (categoryService.checkToUnique(categoryDto.getName())) return ResponseEntity.badRequest().body(new ApiResponse("Error", false, "This category has already exists!!!"));
        try {
            categoryService.addCategory(categoryDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse("Not Found", false, null));
        }
        return ResponseEntity.ok(new ApiResponse("", true, null));
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getCategoryById(@PathVariable int id){
        Category category = categoryService.getById(id);
        if (category==null) {
            return ResponseEntity.badRequest().body(new ApiResponse("Not Found", false, null));
        }
        return ResponseEntity.ok(new ApiResponse("",true,category));
    }
}
