package uz.pdp.services;

import org.springframework.web.multipart.MultipartFile;
import uz.pdp.entities.ImageData;
import uz.pdp.entities.User;
import uz.pdp.projections.UserProjection;
import uz.pdp.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Optional;

import static uz.pdp.util.UploadDirectory.UPLOAD_DIRECTORY;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepo userRepo;
    private final ImageService imageService;

    public void save(User user, MultipartFile image) {
        ImageData save = imageService.save(image);
        user.setImage(save);
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

    public boolean checkToUnique(String phoneNumber) {
        return userRepo.checkToUnique(phoneNumber)!=null;
    }

    public void edit(User user, MultipartFile image) {
        Optional<UserProjection> userById = userRepo.getUserById(user.getId());
        File file = new File(UPLOAD_DIRECTORY + userById.get().getPhotoName());
        file.delete();
        save(user,image);
    }
}

