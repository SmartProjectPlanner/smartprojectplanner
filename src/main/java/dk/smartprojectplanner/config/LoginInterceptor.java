package dk.smartprojectplanner.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;
import org.jspecify.annotations.NonNull;

/**
 * Prevents unauthenticated users from accessing protected pages
 */
public class LoginInterceptor implements HandlerInterceptor {

    /**
     * Checks whether the current user has an authenticated session
     *
     * @param request current HTTP request
     * @param response current HTTP response
     * @param handler the handler selected for the request
     * @return true if the request may continue, otherwise false
     * @throws Exception if the redirect cannot be performed
     */
    @Override
    public boolean preHandle(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull Object handler
    ) throws Exception {
        HttpSession session = request.getSession(false);

        if(session == null ||
        session.getAttribute("userId") == null) {
            response.sendRedirect("/login");
            return false;
        }
        return true;
    }
}
