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

    public ConnectionBuilder(Properties properties) throws SQLException {
        this.properties = properties;
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

    public ResponseData getUsersFromGuild() throws IOException, InterruptedException {
        //TODO you know what to do here
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request;
        request = HttpRequest.newBuilder()
                .uri(URI.create("some bungie URL to receive only one guild"))
                .header("X-API-Key", properties.getProperty("bungie.apiToken"))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return new Gson().fromJson(response.body(), ResponseData.class);

    }
}
