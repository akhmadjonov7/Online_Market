package uz.pdp.projections;

public interface BrandProjection {
    Integer getId();
    String getName();
    String getOwner();
    String getAbout();
    String getImagePath();
    Integer getImageId();
    String getImageContentType();
}
