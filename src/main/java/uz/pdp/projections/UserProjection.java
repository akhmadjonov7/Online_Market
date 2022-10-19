package uz.pdp.projections;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public interface UserProjection {
    Integer getId();

    String getFullName();

    String getUsername();

    String getPhotoName();
}
