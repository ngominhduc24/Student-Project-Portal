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
        User user = (User) request.getSession().getAttribute("user");

        if(user!=null) {
            try {

                if(url.contains("/admin/") && user.getSetting().getId() != 2) throw new Exception();
                if(url.contains("/student/") && user.getSetting().getId() != 1) throw new Exception();
                if(url.contains("/class-manager/") && user.getSetting().getId() != 1 && user.getSetting().getId() != 4) throw new Exception();
                if(url.contains("/project-mentor/") && user.getSetting().getId() != 1 && user.getSetting().getId() != 4) throw new Exception();
                if(url.contains("/subject-manager/") && user.getSetting().getId() != 3) throw new Exception();

            } catch (Exception e) {
                response.sendRedirect("/");
                return;
            }
        }

        List<String> authorUrl = new ArrayList<>();
        authorUrl.add("/admin/");
        authorUrl.add("/student/");
        authorUrl.add("/class-manager/");
        authorUrl.add("/project-mentor/");
        authorUrl.add("/subject-manager/");

        if(checkAccpectUrl(authorUrl, url) && user==null) {
            response.sendRedirect("/login");
            return;
        }

//        List<String> acceptUrl = new ArrayList<>();
//        acceptUrl.add("login");
//        acceptUrl.add(".css");
//        acceptUrl.add(".js");
//        acceptUrl.add("images");
//        acceptUrl.add("register");
//        acceptUrl.add("verify");
//        acceptUrl.add("forgotPassword");
//        acceptUrl.add("reset-password");
//
//
//        if(checkAccpectUrl(acceptUrl, url) || url.charAt(url.length()-1)=='/') {
//
//        } else {
//            User user = (User) request.getSession().getAttribute("user");
//
//            if(user == null) {
//                response.sendRedirect("/");
//                return;
//            }
//        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    public boolean checkAccpectUrl(List<String> acceptUrl, String url) {
        boolean ans = false;
        for(String a : acceptUrl)
            if(url.contains(a)) return true;

        return false;
    }
}

