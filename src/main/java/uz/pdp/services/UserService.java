package uz.pdp.services;

import uz.pdp.entities.User;
import uz.pdp.projections.UserProjection;
import uz.pdp.repositories.UserRepo;
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

    public Page<UserProjection> getAllUsers(int page, int size) {
        return userRepo.getUsers(PageRequest.of(page-1,size));
    }

    public boolean delete(int id) {
        try {
            userRepo.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public UserProjection getUserById(int id) {
        Optional<UserProjection> userById = userRepo.getUserById(id);
        return userById.get();
    }

}

