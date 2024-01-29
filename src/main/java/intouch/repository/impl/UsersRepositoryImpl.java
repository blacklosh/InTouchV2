package intouch.repository.impl;

import lombok.RequiredArgsConstructor;
import intouch.model.User;
import intouch.repository.UsersRepository;

import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class UsersRepositoryImpl implements UsersRepository {
    private final Connection connection;

    private static final String SQL_SELECT_BY_EMAIL = "select * from user_accounts where email = ?";
    private static final String SQL_SELECT_BY_ID = "select * from user_accounts where id = ?";
    private static final String SQL_REGISTER = "insert into user_accounts(name, email, password_hash, id) values(?, ?, ?, uuid_generate_v1())";
    private static final String SQL_DELETED = "delete from user_accounts where id = ?";

    @Override
    public Optional<User> findByEmail(String email) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_EMAIL);
            preparedStatement.setString(1,email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()) {
                return Optional.empty();
            }
            User user = User.builder()
                    .id(resultSet.getObject("id", UUID.class))
                    .name(resultSet.getString("name"))
                    .email(resultSet.getString("email"))
                    .passwordHash(resultSet.getString("password_hash"))
                    .build();
            return Optional.of(user);
        } catch (SQLException throwable) {
            System.out.println("SQL Exception: " + throwable.getLocalizedMessage());
        }
        return Optional.empty();
    }

    @Override
    public User save(User item) {
        // TODO: Обновлять тоже должен уметь
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_REGISTER);
            preparedStatement.setString(1, item.getName());
            preparedStatement.setString(2, item.getEmail());
            preparedStatement.setString(3, item.getPasswordHash());
            preparedStatement.execute();
            return item;
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getLocalizedMessage());
        }
        return null;
    }

    @Override
    public void delete(UUID id) {
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_DELETED);
            statement.setObject(1, id);
            statement.execute();
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getLocalizedMessage());
        }
    }

    @Override
    public Optional<User> findById(UUID id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_ID);
            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()) {
                return Optional.empty();
            }
            User user = User.builder()
                    .id(resultSet.getObject("id", UUID.class))
                    .name(resultSet.getString("name"))
                    .email(resultSet.getString("email"))
                    .passwordHash(resultSet.getString("password_hash"))
                    .build();
            return Optional.of(user);
        } catch (SQLException throwable) {
            System.out.println("SQL Exception: " + throwable.getLocalizedMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        // TODO: Implement
        return null;
    }
}