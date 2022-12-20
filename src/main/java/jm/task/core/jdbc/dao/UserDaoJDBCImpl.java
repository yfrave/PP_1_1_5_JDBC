package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (PreparedStatement preparedStatement = connection
                .prepareStatement("CREATE TABLE IF NOT EXISTS users (id INT NOT NULL AUTO_INCREMENT," +
                        " name VARCHAR(20) NULL, lastName VARCHAR(20) NULL, " +
                        "age INT NULL, PRIMARY KEY (id))")) {
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Таблица уже существует!");
        }

    }

    public void dropUsersTable() {
        try (PreparedStatement preparedStatement = connection
                .prepareStatement("DROP TABLE users")) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Таблица уже удалена!");
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement = connection
                .prepareStatement("INSERT INTO users (name, lastName, age)VALUES ( ?, ?, ?)")) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("Пользователь " + name + " добавлен!");

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка добавления пользователя");
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement = connection
                .prepareStatement("delete from users where id = ?")) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Пользователь с id = " + id + " удалён.");
        } catch (SQLSyntaxErrorException ignore) {
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка удаления пользователей через id");
        }
    }

    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                allUsers.add(user);
                System.out.println("Все пользователи добавлены в базу данных.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка показа пользователей");
        }
        return allUsers;
    }

    public void cleanUsersTable() {
        try (PreparedStatement preparedStatement = connection
                .prepareStatement("TRUNCATE TABLE users;")) {

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка очистки таблицы");
        }
    }
}
