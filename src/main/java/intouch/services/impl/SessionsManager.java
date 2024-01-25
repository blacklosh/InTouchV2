package intouch.services.impl;

import intouch.services.model.Session;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.*;

public class SessionsManager {
    private final Map<String, Session> cache = new HashMap<>();

    public SessionsManager() {}

    public Session getSession(boolean force, HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies() == null ? new Cookie[] {} : request.getCookies();
        Optional<Cookie> optionalCookie = Arrays.stream(cookies)
                .filter(item -> item.getName().equals("JSESSIONID"))
                .findAny();

        Session session = null;
        if(optionalCookie.isPresent()) {
            session = cache.get(optionalCookie.get().getValue());
        }
        if(session != null) {
            return session;
        } else if(force) {
            session = new Session();
            String sessionId = UUID.randomUUID().toString();
            response.addCookie(new Cookie("JSESSIONID", sessionId));
            cache.put(sessionId, session);
            return session;
        }
        return null;
    }
}
