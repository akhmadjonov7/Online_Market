package uz.pdp.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uz.pdp.entities.Category;
import uz.pdp.repositories.CategoryRepo;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepo categoryRepo;

    public Page<Category> allCategory(int page, int size) {
        return categoryRepo.findAll(PageRequest.of(page-1, size));
    }

    public void addCategory(Category category) {
            categoryRepo.save(category);
    }


    public boolean deleteCategory(int id) {
        try {
            categoryRepo.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Category getById(int id) {
        Optional<Category> byId = categoryRepo.findById(id);
        if (byId.isEmpty()) {
            return null;
        }
        return byId.get();
    }

    public boolean checkToUnique(String name) {
        return categoryRepo.checkToUnique(name) != null;
    }
}
