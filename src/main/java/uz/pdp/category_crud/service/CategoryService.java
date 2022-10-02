package uz.pdp.category_crud.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uz.pdp.category_crud.model.Category;
import uz.pdp.category_crud.repo.CategoryRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Page<Category> allCategory(int page, int size) {
        return categoryRepository.findAll(PageRequest.of(page, size));
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
