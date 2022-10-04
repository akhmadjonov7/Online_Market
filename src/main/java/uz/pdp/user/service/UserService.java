package uz.pdp.user.service;

import uz.pdp.user.model.User;
import uz.pdp.user.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepo userRepo;

    public void save(User user) {
        userRepo.save(user);
    }

    public Page<User> getAllUsers(int page, int size) {
        return userRepo.findAll(PageRequest.of(page-1,size));
    }

    public boolean delete(int id) {
        try {
            userRepo.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public User getUserById(int id) {
        Optional<User> userById = userRepo.findById(id);
        return userById.get();
    }

}

