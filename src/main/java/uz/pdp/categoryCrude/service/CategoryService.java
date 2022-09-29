package uz.pdp.categoryCrude.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import uz.pdp.categoryCrude.model.Category;
import uz.pdp.categoryCrude.repo.CategoryRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> allCategory() {
        return categoryRepository.findAll();
    }

    public boolean addCategory(Category category) {
        try {
            categoryRepository.save(category);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public boolean deleteCategory(int id) {
        try {
            categoryRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Object getById(int id) {
        Optional<Category> byId = categoryRepository.findById(id);
        return byId.get();
    }


}
