package com.zb.tablereservation.security.util;

import com.zb.tablereservation.exception.ExceptionCode;
import com.zb.tablereservation.security.domain.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;

public class MySecurityUtil {
    public static CustomUserDetails getCustomUserDetails(){
        Object principal = getObject();
        if(!(principal instanceof CustomUserDetails)){
            throw new RuntimeException(ExceptionCode.AUTHENTICATION_ISSUE.getMessage());
        }
        return (CustomUserDetails) principal;
    }

    private static Object getObject() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private MySecurityUtil() {
    }
}
