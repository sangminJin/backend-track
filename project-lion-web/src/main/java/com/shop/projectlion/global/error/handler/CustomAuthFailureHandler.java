package com.shop.projectlion.global.error.handler;

import com.shop.projectlion.global.error.exception.ErrorCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class CustomAuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        String errorMsg = ErrorCode.LOGIN_ERROR.getMessage();
        errorMsg = URLEncoder.encode(errorMsg, StandardCharsets.UTF_8);

        setDefaultFailureUrl("/login?errorMsg=" + errorMsg);
        super.onAuthenticationFailure(request, response, exception);
    }
}