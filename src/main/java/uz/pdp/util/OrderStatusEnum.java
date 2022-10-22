package uz.pdp.util;

import java.time.DateTimeException;

public enum OrderStatusEnum {
    NEW,
    ACCEPTED,
    DECLINED,
    ON_THE_WAY,
    DELIVERED;
    private static final OrderStatusEnum[] ENUMS = OrderStatusEnum.values();

    public static OrderStatusEnum of(int status){
        if (status < 1 || status > 5) {
            throw new DateTimeException("Invalid value for Status: " + status);
        }
        return ENUMS[status - 1];
    }

}
