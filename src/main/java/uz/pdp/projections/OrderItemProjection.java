package uz.pdp.projections;

public interface OrderItemProjection {
    Integer getProductId();
    String getProductName();
    String getProductImageName();
    Integer getQuantity();
}
