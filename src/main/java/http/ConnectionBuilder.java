package http;

import com.google.gson.Gson;
import models.ResponseData;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.*;
import java.util.Properties;

public class ConnectionBuilder {

    Properties properties;
    Connection connection;
    Statement statement;

    public ConnectionBuilder(Properties properties) throws SQLException {
        this.properties = properties;
        connection = DriverManager.getConnection(properties.getProperty("db.url"));
        statement = connection.createStatement();
        setupConnection();
    }

    public ResponseData getPowerlevel(String membershipId) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request;
        request = HttpRequest.newBuilder()
                .uri(URI.create("https://www.bungie.net/Platform/Destiny2/3/Profile/" + membershipId + "/?lc=de&fmt=true&lcin=true&&components=100,200,205"))
                .header("X-API-Key", properties.getProperty("bungie.apiToken"))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return new Gson().fromJson(response.body(), ResponseData.class);
    }

    public boolean isLightHigher(String user, int light, int classType, long characterId) throws SQLException {
        String query = "SELECT count(*) AS totalCount FROM power WHERE characterId = '" + characterId + "'";
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.next();
        if (resultSet.getInt("totalCount") == 1) {
            query = "SELECT * FROM power WHERE characterId = '" + characterId + "'";
            resultSet = statement.executeQuery(query);
            resultSet.next();
            int lightInDB = Integer.valueOf(resultSet.getString("light"));
            if (lightInDB < light) {
                updateLightInDB(user, light, classType, characterId);
                return true;
            }
        } else {
            updateLightInDB(user, light, classType, characterId);
            return true;
        }
        return false;
    }

    private void updateLightInDB(String user, int light, int classType, long characterId) throws SQLException {
        String query = "INSERT INTO power(user, light, classType, characterId) VALUES('" + user + "', '" + light + "', '" + classType + "', '" + characterId + "');";
        statement.execute(query);
    }

    private void setupConnection() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS power (power_id int PRIMARY KEY AUTO_INCREMENT, user varchar(50) NOT NULL, light varchar(200) NOT NULL, classType varchar(200) NOT NULL, characterId varchar(200) NOT NULL)";
        statement.execute(query);
    }
}
