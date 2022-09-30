package uz.pdp.categoryCrude.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.categoryCrude.model.Category;
import uz.pdp.rest.Api;
import uz.pdp.categoryCrude.service.CategoryService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public HttpEntity<?> showCategoriesList() {
        List<Category> categoryList = categoryService.allCategory();
        return ResponseEntity.ok(new Api("", true, categoryList));
    }


    @PostMapping
    public HttpEntity<?> addSave(@RequestBody Category category) {
        categoryService.addCategory(category);
        return ResponseEntity.ok(new Api("", true, null));
    }

    @DeleteMapping("/delete/{id}")
    public HttpEntity<?> delete(@PathVariable int id) {
        boolean delete = categoryService.deleteCategory(id);
        if (delete) {
            return ResponseEntity.ok(new Api("", true, null));
        }
        return ResponseEntity.ok(new Api("You Cannot Delete This Category", false, null));
    }


    @PutMapping("/editsave")
    public HttpEntity<?> editsave(@RequestBody Category category) {
        boolean update = categoryService.addCategory(category);
        if (update) {
            return ResponseEntity.ok(new Api("", true, null));
        }
        return ResponseEntity.ok(new Api("Category not found", false, null));
    }



}
