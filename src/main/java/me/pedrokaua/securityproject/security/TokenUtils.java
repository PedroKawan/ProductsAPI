package me.pedrokaua.securityproject.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.util.Collections;

public class TokenUtils {

    public static Authentication tokenDecoder(HttpServletRequest request){
        if(request.getHeader("Authorization").equals("Basic cGVkcm86MTAyMDMw")
            || request.getHeader("Authorization").equals("Bearer tokentoken")){
            return new UsernamePasswordAuthenticationToken("user", null, Collections.emptyList());
        }
        return null;
    }
}
