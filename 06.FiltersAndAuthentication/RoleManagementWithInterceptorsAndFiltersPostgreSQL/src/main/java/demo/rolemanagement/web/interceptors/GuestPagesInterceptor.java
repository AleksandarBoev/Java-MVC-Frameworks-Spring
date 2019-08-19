package demo.rolemanagement.web.interceptors;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class GuestPagesInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getSession().getAttribute("ROLE") != null) {
            request.getSession().setAttribute("errorMessage", "Only guests allowed!");
            response.sendRedirect("/custom-error");
        }
        return true;
    }
}
