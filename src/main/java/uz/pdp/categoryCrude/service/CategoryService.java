package uz.pdp.categoryCrude.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.categoryCrude.model.Category;
import uz.pdp.categoryCrude.repo.CategoryRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

public List<Category> allCategory () {
    return categoryRepository.findAll();
}

public void addCategory (Category category ){
    categoryRepository.save(category);
}


public boolean  deleteCategory (int id ) {
    try {
        categoryRepository.deleteById(id);
        return true;
    } catch (Exception e) {
        return false;
    }
}

public Object editById (int id ) {
    Optional<Category> byId = categoryRepository.findById(id);
    try {
            if (byId.isPresent())
        return  byId.get();

    }catch (Exception e) {

    }
    return  null;
}





}
