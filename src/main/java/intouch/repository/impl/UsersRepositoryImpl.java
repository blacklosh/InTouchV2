package intouch.repository.impl;

import intouch.model.User;
import intouch.repository.UsersRepository;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class UsersRepositoryImpl implements UsersRepository {
    private final Connection connection;

    private static final String SQL_SELECT_BY_EMAIL = "select * from user_accounts where email = ?";
    private static final String SQL_SELECT_BY_ID = "select * from user_accounts where id = ?";
    private static final String SQL_REGISTER = "insert into user_accounts(name, email, password_hash, avatar_id, id) values(?, ?, ?, ?, uuid_generate_v1())";
    private static final String SQL_UPDATE = "update user_accounts set name=?, email=?, password_hash=?, avatar_id=? where id=?";
    private static final String SQL_DELETED = "delete from user_accounts where id = ?";
    private static final String SQL_UPDATE_AVATAR = "update user_accounts set avatar_id=? where id=?";

    @Override
    public void updateAvatarForUser(UUID userId, UUID avatarId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_AVATAR);
            preparedStatement.setObject(1, avatarId);
            preparedStatement.setObject(2, userId);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

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
                    .avatarId(resultSet.getObject("avatar_id",UUID.class))
                    .build();
            return Optional.of(user);
        } catch (SQLException throwable) {
            System.out.println("SQL Exception: " + throwable.getLocalizedMessage());
        }
        return Optional.empty();
    }

    @Override
    public User save(User item) {
        try {
            if(item.getId() == null) {
                PreparedStatement preparedStatement = connection.prepareStatement(
                        SQL_REGISTER,
                        new String[]{"id"}
                );
                preparedStatement.setString(1, item.getName());
                preparedStatement.setString(2, item.getEmail());
                preparedStatement.setString(3, item.getPasswordHash());
                preparedStatement.executeUpdate();
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                generatedKeys.next();
                item.setId((UUID) generatedKeys.getObject(1));
                return item;
            } else {
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE);
                preparedStatement.setString(1, item.getName());
                preparedStatement.setString(2, item.getEmail());
                preparedStatement.setString(3, item.getPasswordHash());
                preparedStatement.setObject(4, item.getId());
                preparedStatement.setObject(5, item.getAvatarId());
                preparedStatement.executeUpdate();
                return item;
            }
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
                    .avatarId(resultSet.getObject("avatar_id", UUID.class))
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