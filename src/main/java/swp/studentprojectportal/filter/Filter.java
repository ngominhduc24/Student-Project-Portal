package swp.studentprojectportal.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import swp.studentprojectportal.model.User;

import java.io.IOException;

@Component
public class Filter implements jakarta.servlet.Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String url = request.getRequestURL().toString();
//        System.out.println(url);

        if(url.contains("login") || url.contains(".css") || url.contains(".js") ||
                url.contains("images") || url.contains("register") ||
                url.charAt(url.length()-1)=='/') {

        } else {
            User user = (User) request.getSession().getAttribute("user");

            if(user == null) {
                response.sendRedirect("/");
                return;
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
