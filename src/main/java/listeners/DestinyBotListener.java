package listeners;

import http.ConnectionBuilder;
import models.ResponseData;
import models.profile.CharacterResponse;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import services.DataService;
import services.MessageService;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DestinyBotListener extends ListenerAdapter {

    ConnectionBuilder connectionBuilder;
    Properties properties;
    DataService dataService;
    MessageService messageService;

    public DestinyBotListener(Properties properties) throws SQLException {
        connectionBuilder = new ConnectionBuilder(properties);
        dataService = new DataService(properties);
        messageService = new MessageService();
        this.properties = properties;
    }

    private void schedulePowerLevelTask(TextChannel textChannel) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Task started at " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy - HH:mm:ss")));
                getAndUpdateUsersFromGuild();
                getPowerlevelUpdate(textChannel);
                System.out.println("Task finished at " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy - HH:mm:ss")));
            }
        };
        Timer timer = new Timer("Timer");
        long delay = 5000L;
        timer.schedule(task, delay, 3600000L);
    }

    private void getAndUpdateUsersFromGuild() {
        //TODO call connectionBuilder and receive all current members of the guild
        //TODO call dataservice, check if users in database is up to date, update user (and possibly power) table accordingly
    }

    private void getPowerlevelUpdate(TextChannel textChannel) {
        try {
            for (Map.Entry<String, String> approvedId : dataService.getAllUsers().entrySet()) {
                ResponseData responseData = connectionBuilder.getPowerlevel(approvedId.getValue());
                if(responseData != null && responseData.getResponse() != null && responseData.getResponse().getCharacters() != null) {
                    for (Map.Entry<String, CharacterResponse> characterResponseEntry : responseData.getResponse().getCharacters().getData().entrySet()) {
                        CharacterResponse characterResponse = characterResponseEntry.getValue();
                        if (dataService.isLightHigher(approvedId.getKey(), characterResponse.getLight(), characterResponse.getClassType(), characterResponse.getCharacterId())) {
                            System.out.println("DEBUG: ApproveID: " + approvedId.getKey() + " / " + approvedId.getValue() + "\nLight: " + characterResponse.getLight() + "\nClasstype: " + characterResponse.getClassType() + "\nCharacterID: " + characterResponse.getCharacterId());
                            textChannel.sendMessageEmbeds(messageService.sendPowerlevelUpdate(characterResponse, approvedId.getKey()).build()).queue();
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println("SQLException thrown: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("IOException thrown: " + ex.getMessage());
        } catch (InterruptedException ex) {
            System.out.println("InterruptedException thrown: " + ex.getMessage());
        } catch (Exception ex) {
            System.out.println("Exception thrown: " + ex.getMessage());
        }
    }

    @Override
    public void onReady(ReadyEvent event){
        JDA jda = event.getJDA();
        Guild guild = jda.getGuildById(properties.getProperty("discord.dedicatedServer"));
        schedulePowerLevelTask(guild.getTextChannelById(properties.getProperty("discord.dedicatedTextChannel")));
    }
}
