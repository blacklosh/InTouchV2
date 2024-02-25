package intouch.util;

import javax.servlet.ServletContext;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

public class PropertyReader {
    public static String getProperty(String prop, ServletContext servletContext) {
        try {
            URL url = PropertyReader.class.getResource("/application.properties");
            if (url == null) {
                System.err.println("PROPERTIES FILE NOT FOUND");
                return null;
            }
            String path = url.getFile();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            while (bufferedReader.ready()) {
                String read = bufferedReader.readLine();
                if(read.startsWith(prop)) {
                    return read.substring(prop.length() + 1);
                }
            }
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
        return null;
    }
}