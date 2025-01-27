package com.example.board.common.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//        SecurityContextHolder 객체 안에, SecurityContext 객체 안에, Authentication
        HttpSession httpSession = request.getSession();
        httpSession.setAttribute("email", SecurityContextHolder.getContext().getAuthentication().getName());
        response.sendRedirect("/");
    }
}
