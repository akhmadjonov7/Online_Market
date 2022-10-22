package uz.pdp.util;

import java.time.DateTimeException;

public enum PermissionEnum {
    CAN_DELETE_CATEGORY,
    CAN_DELETE_BRAND,
    CAN_DELETE_USER,
    CAN_CHANGE_ORDER_STATUS,
    CAN_DELETE_PRODUCT,
    CAN_DELETE_CH_VALUE,
    CAN_DELETE_CHARACTERISTIC;
    private static final PermissionEnum[] ENUMS = PermissionEnum.values();

    public static PermissionEnum of(int permissionId){
        if (permissionId < 1 || permissionId > 5) {
            throw new DateTimeException("Invalid value for Status: " + permissionId);
        }
        return ENUMS[permissionId - 1];
    }
}
