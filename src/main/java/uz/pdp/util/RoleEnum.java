package uz.pdp.util;

import java.time.DateTimeException;

public enum RoleEnum {
    ROLE_SUPER_ADMIN,
    ROLE_ADMIN,
    ROLE_USER;

    private static final RoleEnum[] ENUMS = RoleEnum.values();

    public static RoleEnum of(int roleId){
        if (roleId < 1 || roleId > 5) {
            throw new DateTimeException("Invalid value for Status: " + roleId);
        }
        return ENUMS[roleId - 1];
    }
}
