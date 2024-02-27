package intouch.repository.impl;

import intouch.model.Post;
import intouch.model.User;
import intouch.repository.PostsRepository;
import lombok.RequiredArgsConstructor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class PostsRepositoryImpl implements PostsRepository {
    private final Connection connection;

    private static final String SQL_SELECT_BY_ID = "select p.id as post_id, p.text, p.creation_date, u.id as user_id, u.name, u.email, u.avatar_id, u.password_hash from posts p left join user_accounts u on p.author_id = u.id where p.id = ?";
    private static final String SQL_SELECT_ALL = "select p.id as post_id, p.text, p.creation_date, u.id as user_id, u.name, u.email, u.avatar_id, u.password_hash from posts p left join user_accounts u on p.author_id = u.id;";
    private static final String SQL_SAVE = "insert into posts(author_id, text,creation_date, id) values(?, ?, ?, uuid_generate_v1())";
    private static final String SQL_UPDATE = "update posts set author_id=?, text=?, creation_date=? WHERE id=?";
    private static final String SQL_DELETE = "delete from posts where id = ?";

    @Override
    public Optional<Post> findById(UUID id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_ID);
            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()) {
                return Optional.empty();
            }
            Post post = Post.builder()
                    .id(resultSet.getObject("post_id", UUID.class))
                    .author(User.builder()
                            .id(resultSet.getObject("user_id",UUID.class))
                            .name(resultSet.getString("name"))
                            .email(resultSet.getString("email"))
                            .avatarId((UUID) resultSet.getObject("avatar_id"))
                            .passwordHash(resultSet.getString("password_hash"))
                            .build())
                    .text(resultSet.getString("text"))
                    .creationDate(resultSet.getTimestamp("creation_date").toInstant())
                    .build();
            return Optional.of(post);
        } catch (SQLException throwable) {
            System.out.println("SQL Exception: " + throwable.getLocalizedMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Post> findAll() {
        List<Post> result = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Post post = Post.builder()
                        .id(resultSet.getObject("post_id", UUID.class))
                        .author(User.builder()
                                .id(resultSet.getObject("user_id",UUID.class))
                                .name(resultSet.getString("name"))
                                .email(resultSet.getString("email"))
                                .avatarId((UUID) resultSet.getObject("avatar_id"))
                                .passwordHash(resultSet.getString("password_hash"))
                                .build())
                        .text(resultSet.getString("text"))
                        .creationDate(resultSet.getTimestamp("creation_date").toInstant())
                        .build();
                result.add(post);
            }
        } catch (SQLException throwable) {
            System.out.println("SQL Exception: " + throwable.getLocalizedMessage());
        }
        return result;
    }

    @Override
    public Post save(Post item) {
        try {
            if (item.getId() == null) {
                PreparedStatement preparedStatement = connection.prepareStatement(
                        SQL_SAVE,
                        new String[]{"id"}
                );
                preparedStatement.setObject(1, item.getAuthor().getId());
                preparedStatement.setString(2, item.getText());
                preparedStatement.setTimestamp(3, Timestamp.from(item.getCreationDate()));
                preparedStatement.executeUpdate();
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                generatedKeys.next();
                item.setId((UUID) generatedKeys.getObject(1));
                return item;
            } else {
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE);
                preparedStatement.setObject(1, item.getAuthor().getId());
                preparedStatement.setString(2, item.getText());
                preparedStatement.setTimestamp(3,Timestamp.from(item.getCreationDate()));
                preparedStatement.setObject(4, item.getId());
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
            PreparedStatement statement = connection.prepareStatement(SQL_DELETE);
            statement.setObject(1, id);
            statement.execute();
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getLocalizedMessage());
        }
    }
}