package intouch.listeners;

import intouch.mapper.PostMapper;
import intouch.mapper.UserMapper;
import intouch.mapper.impl.PostMapperImpl;
import intouch.mapper.impl.UserMapperImpl;
import intouch.repository.FileRepository;
import intouch.repository.PostsRepository;
import intouch.repository.UsersRepository;
import intouch.repository.impl.FileRepositoryImpl;
import intouch.repository.impl.PostsRepositoryImpl;
import intouch.repository.impl.UsersRepositoryImpl;
import intouch.services.*;
import intouch.services.impl.*;
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
    private static String DB_PASSWORD;
    private static String DB_URL;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext context = servletContextEvent.getServletContext();
        String dbUsername = PropertyReader.getProperty("DB_USERNAME", context);
        DB_PASSWORD = PropertyReader.getProperty("DB_PASSWORD",context);
        DB_URL = PropertyReader.getProperty("DB_URL",context);
        System.out.println("DB_USERNAME: " + dbUsername);
        System.out.println("Начинаю инициализировать контекст");
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("POSTGRESQL DRIVER LOADED");
        } catch (ClassNotFoundException e) {
            System.err.println("DRIVER NOT FOUND");
        }
        try {
            connection = DriverManager.getConnection(DB_URL, dbUsername,DB_PASSWORD);
        } catch (SQLException e) {
            System.err.println("CONNECTION NOT CREATED: " + e.getLocalizedMessage());
        }
        
        UsersRepository usersRepository = new UsersRepositoryImpl(connection);
        PasswordEncoder passwordEncoder = new PasswordEncoderImpl();
        UserMapper userMapper = new UserMapperImpl(passwordEncoder);
        UsersService usersService = new UsersServiceImpl(usersRepository);
        AuthorizationService authorizationService = new AuthorizationServiceImpl(usersRepository, userMapper, passwordEncoder);
        PostsRepository postsRepository = new PostsRepositoryImpl(connection);
        PostMapper postMapper = new PostMapperImpl(userMapper);
        PostsService postsService = new PostsServiceImpl(postsRepository, postMapper);
        FileRepository fileRepository = new FileRepositoryImpl(connection);
        FileService fileService = new FileServiceImpl("/Users/artur/Documents/uploads/", fileRepository, usersRepository);

        context.setAttribute("fileService", fileService);
        context.setAttribute("authorizationService", authorizationService);
        context.setAttribute("usersService", usersService);
        context.setAttribute("postsService", postsService);
        System.out.println("Успешно инициализировал контекст");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
}