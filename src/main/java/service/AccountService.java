package service;

import database.Database;
import entity.Account;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountService {
    private static final String SELECT_ALL_ACCOUNTS_QUERY = "SELECT * FROM accounts";
    private static final String SAVE_ACCOUNT_QUERY = "INSERT INTO accounts (client_id,  number, value) VALUES (?,?,?)";
    private static final String UPDATE_ACCOUNT_QUERY = "UPDATE accounts SET client_id = ?, number = ?, value = ? WHERE id = ?";
    private static final String DELETE_ACCOUNT_QUERY = "DELETE FROM clients WHERE id = ?";
    private static final String SEARCH_NUMBER_ABOVE_VALUE_QUERY = "SELECT number FROM accounts WHERE value > ?";

    public List<Account> getAll() {
        List<Account> accounts = new ArrayList<>();

        try (Connection connection = Database.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_ACCOUNTS_QUERY);
            while (resultSet.next()) {
                Account account = new Account();
                account.setId(resultSet.getLong("id"));
                account.setClient_id(resultSet.getLong("client_id"));
                account.setNumber(resultSet.getString("number"));
                account.setValue(resultSet.getDouble("value"));
                accounts.add(account);
            }
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    public void saveRecord(Account account) {
        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_ACCOUNT_QUERY)) {
            preparedStatement.setLong(1, account.getClientId());
            preparedStatement.setString(2, account.getNumber());
            preparedStatement.setDouble(3, account.getValue());
            preparedStatement.execute();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateAccount(Account account, long clientId, String name, double value, long id) {
        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ACCOUNT_QUERY)) {
            preparedStatement.setLong(1, clientId);
            preparedStatement.setString(2, name);
            preparedStatement.setDouble(3, value);
            preparedStatement.setLong(4, id);
            preparedStatement.execute();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAccount(Account account, long id) {
        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ACCOUNT_QUERY)) {
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> getAccountNumbersAboveValue(double value) {
        ArrayList<String> numbers = new ArrayList<String>();

        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_NUMBER_ABOVE_VALUE_QUERY)) {
            preparedStatement.setDouble(1, value);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                numbers.add(resultSet.getString("number"));
            }
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return numbers;
    }

}
