package services;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class DataService {

    Properties properties;
    Connection connection;
    public DataService(Properties properties) throws SQLException {
        this.properties = properties;
        connection = DriverManager.getConnection(properties.getProperty("db.url"));
        setupConnection();
    }

    public boolean isLightHigher(String user, int light, int classType, long characterId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT count(*) AS totalCount FROM power WHERE characterId = ?");
        preparedStatement.setLong(1, characterId);
        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()) {
            if (resultSet.getInt("totalCount") == 1) {
                preparedStatement = connection.prepareStatement("SELECT * FROM power WHERE characterId = ?");
                preparedStatement.setLong(1, characterId);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    int lightInDB = Integer.valueOf(resultSet.getString("light"));
                    if (lightInDB < light) {
                        updateLightInDB(light, characterId);
                        return true;
                    }
                }
            } else {
                insertLightInDB(user, light, classType, characterId);
                return true;
            }
        }
        return false;
    }

    public Map<String, String> getAllUsers() throws SQLException{
        Map<String, String> resultMap = new HashMap<>();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT u.username, u.bungieID FROM users as u");
        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()) {
            resultMap.put(resultSet.getString("u.username"), resultSet.getString("u.bungieID"));
        }
        return resultMap;
    }

    private void insertLightInDB(String user, int light, int classType, long characterId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO power(user, light, classType, characterId) VALUES(?,?,?,?)");
        preparedStatement.setString(1, user);
        preparedStatement.setInt(2, light);
        preparedStatement.setInt(3, classType);
        preparedStatement.setLong(4, characterId);
        preparedStatement.execute();
    }

    private void updateLightInDB(int light, long characterId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE power SET light = ? WHERE characterId = ?");
        preparedStatement.setInt(1, light);
        preparedStatement.setLong(2, characterId);
        preparedStatement.executeUpdate();
    }

    private void setupConnection() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS users (user_id INT AUTO_INCREMENT, username VARCHAR(50) NOT NULL, bungieID VARCHAR(250) UNIQUE, PRIMARY KEY (user_id));");
        preparedStatement.execute();
        preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS power (power_id INT AUTO_INCREMENT, f_user_id INT NOT NULL, light varchar(200) NOT NULL, classType varchar(200) NOT NULL, characterId varchar(200) NOT NULL, PRIMARY KEY(power_id), FOREIGN KEY (f_user_id) REFERENCES users(user_id));");
        preparedStatement.execute();
    }
}
