package service;

import database.Database;
import entity.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientService {
    private static final String SELECT_ALL_CLIENTS_QUERY = "SELECT * FROM clients";
    private static final String SAVE_CLIENT_QUERY = "INSERT INTO clients (name,  email, phone, about, age) VALUES (?,?,?,?,?)";
    private static final String UPDATE_CLIENT_NAME_QUERY = "UPDATE clients SET name = ?, email = ?, phone = ?, about = ?, age =? WHERE id = ?";
    private static final String DELETE_CLIENT_QUERY = "DELETE FROM clients WHERE id = ?";
    private static final String SEARCH_CLIENT_BY_PHONE_NUMBER_QUERY = "SELECT * FROM clients WHERE phone = ?";
    private static final String GET_CLIENTS_WITH_ACCOUNTS = "SELECT * FROM clients WHERE id IN (SELECT DISTINCT client_id FROM accounts)";
    // запрос на  join 3  таблиц
    private static final String GET_NAME_EMAIL_AND_ALIAS =
            "SELECT c.name, c.email, s.alias FROM clients c JOIN client_status cs ON cs.client_id = c.id  JOIN statuses s ON s.id = cs.status_id WHERE c.age > 18";


    public List<Client> getAll() {
        List<Client> clients = new ArrayList<>();
        try (Connection connection = Database.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_CLIENTS_QUERY);
            while (resultSet.next()) {
                Client client = new Client();
                client.setId(resultSet.getLong("id"));
                client.setName(resultSet.getString("name"));
                client.setEmail(resultSet.getString("email"));
                client.setPhone(resultSet.getLong("phone"));
                client.setAbout(resultSet.getString("about"));
                client.setAge(resultSet.getInt("age"));
                clients.add(client);
            }
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }

    public void saveRecord(Client client) {
        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_CLIENT_QUERY)) {
            preparedStatement.setString(1, client.getName());
            preparedStatement.setString(2, client.getEmail());
            preparedStatement.setLong(3, client.getPhone());
            preparedStatement.setString(4, client.getAbout());
            preparedStatement.setInt(5, client.getAge());
            preparedStatement.execute();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateClientName(Client client, String name, String email, long phone, String about, int age, long id) {
        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CLIENT_NAME_QUERY)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setLong(3, phone);
            preparedStatement.setString(4, about);
            preparedStatement.setInt(5, age);
            preparedStatement.setLong(6, id);
            preparedStatement.execute();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteClient(Client client, long id) {
        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CLIENT_QUERY)) {
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Client getClientByPhoneNumber(long phone) {
        Client client = new Client();
        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_CLIENT_BY_PHONE_NUMBER_QUERY)) {
            preparedStatement.setLong(1, phone);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                client.setId(resultSet.getLong("id"));
                client.setName(resultSet.getString("name"));
                client.setEmail(resultSet.getString("email"));
                client.setPhone(resultSet.getLong("phone"));
                client.setAbout(resultSet.getString("about"));
                client.setAge(resultSet.getInt("age"));
            }
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return client;
    }

    public List<Client> getClientsWithAccounts() {
        List<Client> clients = new ArrayList<>();
        try (Connection connection = Database.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(GET_CLIENTS_WITH_ACCOUNTS);
            while (resultSet.next()) {
                Client client = new Client();
                client.setId(resultSet.getLong("id"));
                client.setName(resultSet.getString("name"));
                client.setEmail(resultSet.getString("email"));
                client.setPhone(resultSet.getLong("phone"));
                client.setAbout(resultSet.getString("about"));
                client.setAge(resultSet.getInt("age"));
                clients.add(client);
            }
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }
}
