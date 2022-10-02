package uz.pdp.category_crud.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uz.pdp.category_crud.model.Category;
import uz.pdp.rest.Api;
import uz.pdp.category_crud.service.CategoryService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public HttpEntity<?> showCategoriesList(@RequestParam (name = "page",defaultValue = "1") int page, @RequestParam(name = "size",defaultValue = "1") int size) {
        Page<Category> categoryPage = categoryService.allCategory(page, size);
        return ResponseEntity.ok(new Api("", true, categoryPage));
    }

    @PostMapping
    public HttpEntity<?> addSave(@Valid @RequestBody Category category, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.ok(new Api("",false,bindingResult.getAllErrors()));
        }
        categoryService.addCategory(category);
        return ResponseEntity.ok(new Api("", true, null));
    }

    @DeleteMapping("/delete/{id}")
    public HttpEntity<?> delete(@PathVariable int id) {
        boolean delete = categoryService.deleteCategory(id);
        if (delete) {
            return ResponseEntity.ok(new Api("", true, null));
        }
        return ResponseEntity.ok(new Api("You cannot delete this category",false,null));
    }


    @PutMapping("/editsave")
    public HttpEntity<?> editsave(@RequestBody Category category,BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.ok(new Api("",false,bindingResult.getAllErrors()));
        }
        boolean update = categoryService.addCategory(category);
        if (update) {
            return ResponseEntity.ok(new Api("", true, null));
        }
        return ResponseEntity.ok(new Api("Not Found",false,null));
    }



}
