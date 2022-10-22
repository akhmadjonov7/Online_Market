package uz.pdp.projections;


import org.springframework.beans.factory.annotation.Value;
import uz.pdp.entities.Role;

import java.util.List;

public interface UserProjection {
    Integer getId();

    String getFullName();

    String getEmail();

    String getPhotoName();

    @Value("#{@userRepo.getUserRoles(target.id)}")
    List<RoleProjection> getUserRoles();

    @Value("#{@userRepo.getUserPermissions(target.id)}")
    List<ProductProjection> getUserPermissions();
}
