package uz.pdp.projections;


public interface OrderProjection {
    Integer getId();
    Integer getUserId();
    String getTo();
    String getStatus();
    Double getTotalPrice();
}
