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

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebListener
public class ContextListener implements ServletContextListener {

    //TODO вынести эти поля в параметры application.properties
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "qwerty";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/intouchdb";

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
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

        ServletContext context = servletContextEvent.getServletContext();

        SessionsManager sessionsManager = new SessionsManager();
        UsersRepository usersRepository = new UsersRepositoryImpl(connection);
        PasswordEncoder passwordEncoder = new PasswordEncoderImpl();
        UserMapper userMapper = new UserMapperImpl(passwordEncoder);
        AuthorizationService authorizationService = new AuthorizationServiceImpl(usersRepository, userMapper, passwordEncoder);

        context.setAttribute("sessionsManager", sessionsManager);
        context.setAttribute("authorizationService", authorizationService);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
