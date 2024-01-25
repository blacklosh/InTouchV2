package intouch.util;

import javax.servlet.ServletContext;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PropertyReader {
    public static String getProperty(String prop,ServletContext servletContext) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(servletContext.getResourceAsStream("/resources/application.properties")));
            while (bufferedReader.ready()) {
                String read = bufferedReader.readLine();
                if(read.startsWith(prop)) {
                    return read.substring(prop.length() + 1);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}