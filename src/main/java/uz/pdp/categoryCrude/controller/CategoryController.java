package uz.pdp.categoryCrude.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.pdp.categoryCrude.model.Category;
import uz.pdp.categoryCrude.rest.Api;
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
    public HttpEntity<?> add_save(@RequestBody Category category) {
        System.out.println("category =" + category);
        categoryService.addCategory(category);
        return ResponseEntity.ok(new Api("", true, null));
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        System.out.println("id = " + id);
        categoryService.deleteCategory(id);
        return "redirect:/cars";
    }


    @PostMapping("/editsave")

    public String editsave(Category category) {
        System.out.println(category);
        categoryService.addCategory(category);
        return "redirect:/category";
    }

    @GetMapping("/edit/{id}")
    public String editById(@PathVariable int id, Model model) {
        System.out.println(id);
        Category category = (Category) categoryService.editById(id);
        System.out.println("car = " + categoryService);
        model.addAttribute("editcar", categoryService);
        return "edit_category";
    }


}
