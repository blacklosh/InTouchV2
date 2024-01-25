package intouch.listeners;

import intouch.mapper.UserMapper;
import intouch.mapper.impl.UserMapperImpl;
import intouch.repository.UsersRepository;
import intouch.repository.impl.UsersRepositoryImpl;
import intouch.services.AuthorizationService;
import intouch.services.PasswordEncoder;
import intouch.services.impl.AuthorizationServiceImpl;
import intouch.services.impl.PasswordEncoderImpl;
import intouch.services.impl.SessionsManager;
import intouch.util.PropertyReader;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Slf4j
@WebListener
public class ContextListener implements ServletContextListener {
    private static String DB_USERNAME;
    private static String DB_PASSWORD;
    private static String DB_URL;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext context = servletContextEvent.getServletContext();
        DB_USERNAME = PropertyReader.getProperty("DB_USERNAME",context);
        DB_PASSWORD = PropertyReader.getProperty("DB_PASSWORD",context);
        DB_URL = PropertyReader.getProperty("DB_URL",context);
        System.out.println("DB_USERNAME: " + DB_USERNAME);
        System.out.println("Начинаю инициализировать контекст");
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("POSTGRESQL DRIVER LOADED");
        } catch (ClassNotFoundException e) {
            System.err.println("DRIVER NOT FOUND");
        }
        try {
            connection = DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASSWORD);
        } catch (SQLException e) {
            System.err.println("CONNECTION NOT CREATED: " + e.getLocalizedMessage());
        }


        SessionsManager sessionsManager = new SessionsManager();
        UsersRepository usersRepository = new UsersRepositoryImpl(connection);
        PasswordEncoder passwordEncoder = new PasswordEncoderImpl();
        UserMapper userMapper = new UserMapperImpl(passwordEncoder);
        AuthorizationService authorizationService = new AuthorizationServiceImpl(usersRepository, userMapper, passwordEncoder);

        context.setAttribute("sessionsManager", sessionsManager);
        context.setAttribute("authorizationService", authorizationService);
        System.out.println("Успешно инициализировал контекст");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
