package swp.studentprojectportal.utils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import swp.studentprojectportal.model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class Filter implements jakarta.servlet.Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String url = request.getRequestURL().toString();
        List<String> acceptUrl = new ArrayList<>();
        acceptUrl.add("login");
        acceptUrl.add(".css");
        acceptUrl.add(".js");
        acceptUrl.add("images");
        acceptUrl.add("register");
        acceptUrl.add("verify");
        acceptUrl.add("forgotPassword");
        acceptUrl.add("reset-password");


        if(checkAccpectUrl(acceptUrl, url) || url.charAt(url.length()-1)=='/') {

        } else {
            User user = (User) request.getSession().getAttribute("user");

            if(user == null) {
                response.sendRedirect("/");
                return;
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    public boolean checkAccpectUrl(List<String> acceptUrl, String url) {
        boolean ans = false;
        for(String a : acceptUrl)
            if(url.contains(a)) return true;

        return false;
    }
}

