package intouch.services.model;

import java.util.HashMap;
import java.util.Map;

public class Session {
    private Map<String, Object> data = new HashMap<>();

    public void setAttribute(String key, Object value) {
        data.put(key, value);
    }

    public void removeAttribute(String key) {
        data.remove(key);
    }

    public Object getAttribute(String key) {
        return data.get(key);
    }
}
