package intouch.filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class AuthorizationFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        if(request.getRequestURI().contains("resources")) {
            chain.doFilter(request, response);
            return;
        }
        // берем сессию у запроса
        // берем только существующую, если сессии не было - то вернет null
        HttpSession session = request.getSession(false);
        // флаг, аутентифицирован ли пользователь
        boolean isAuthenticated = false;
        // существует ли сессия вообще?
        boolean sessionExists = session != null;
        // идет ли запрос на страницу входа или регистрации?
        boolean isRequestOnAuthPage = request.getRequestURI().contains("signin") ||
                request.getRequestURI().contains("signup");

        // если сессия есть
        if (sessionExists) {
            // проверим, есть ли атрибует user?
            isAuthenticated = session.getAttribute("user") != null;
        }

        // если авторизован и запрашивает не открытую страницу или если не авторизован и запрашивает открытую
        if (isAuthenticated && !isRequestOnAuthPage || !isAuthenticated && isRequestOnAuthPage) {
            // отдаем ему то, что он хочет
            chain.doFilter(request, response);
        } else if (isAuthenticated && isRequestOnAuthPage) {
            // пользователь аутенцифицирован и запрашивает страницу входа
            // - отдаем ему профиль
            response.sendRedirect(request.getContextPath() + "/profile");
        } else {
            // если пользователь не аутенцицицирован и запрашивает другие страницы
            response.sendRedirect(request.getContextPath() + "/signin");
        }
    }
}