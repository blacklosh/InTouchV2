package intouch.repository.impl;

import intouch.model.FileInfo;
import intouch.repository.FileRepository;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class FileRepositoryImpl implements FileRepository {
    private final Connection connection;

    private static final String SQL_SELECT_BY_ID = "select * from file_info where id=?";
    private static final String SQL_SELECT_ALL = "select * from file_info";
    private static final String SQL_SAVE = "insert into file_info(original_file_name,storage_file_name,size,type,id)values(?, ?, ?, ?, uuid_generate_v1())";
    private static final String SQL_UPDATE = "update file_info set original_file_name=?, storage_file_name=?, size=?, type=? where id=?";
    private static final String SQL_DELETE = "delete from file_info where id = ?";

    @Override
    public Optional<FileInfo> findById(UUID id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_ID);
            preparedStatement.setObject(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()) {
                return Optional.empty();
            }
            FileInfo fileInfo = FileInfo.builder()
                    .id(resultSet.getObject("id", UUID.class))
                    .originalFileName(resultSet.getString("original_file_name"))
                    .storageFileName(resultSet.getString("storage_file_name"))
                    .size(resultSet.getLong("size"))
                    .type(resultSet.getString("type"))
                    .build();
            return Optional.of(fileInfo);
        } catch (SQLException throwable) {
            System.out.println("SQL Exception: " + throwable.getLocalizedMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<FileInfo> findAll() {
        List<FileInfo> result = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                FileInfo fileInfo = FileInfo.builder()
                        .id(resultSet.getObject("id", UUID.class))
                        .originalFileName(resultSet.getString("original_file_name"))
                        .storageFileName(resultSet.getString("storage_file_name"))
                        .size(resultSet.getLong("size"))
                        .type(resultSet.getString("type"))
                        .build();
                result.add(fileInfo);
            }
        } catch (SQLException throwable) {
            System.out.println("SQL Exception: " + throwable.getLocalizedMessage());
        }
        return result;
    }

    @Override
    public FileInfo save(FileInfo item) {
        try {
            if (item.getId() == null) {
                PreparedStatement preparedStatement = connection.prepareStatement(
                        SQL_SAVE,
                        new String[]{"id"}
                );
                preparedStatement.setString(1, item.getOriginalFileName());
                preparedStatement.setString(2, item.getStorageFileName());
                preparedStatement.setLong(3,item.getSize());
                preparedStatement.setString(4,item.getType());
                preparedStatement.executeUpdate();
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                generatedKeys.next();
                item.setId((UUID) generatedKeys.getObject(1));
                return item;
            } else {
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE);
                preparedStatement.setString(1, item.getOriginalFileName());
                preparedStatement.setString(2, item.getStorageFileName());
                preparedStatement.setLong(3,item.getSize());
                preparedStatement.setString(4,item.getType());
                preparedStatement.setObject(5, item.getId());
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
