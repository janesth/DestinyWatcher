package listeners;

import http.ConnectionBuilder;
import models.ResponseData;
import models.profile.CharacterResponse;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DestinyBotListener extends ListenerAdapter {

    ConnectionBuilder connectionBuilder;
    HashMap<String, String> approvedIds;
    Properties properties;

    public DestinyBotListener(Properties properties) throws SQLException {
        connectionBuilder = new ConnectionBuilder(properties);
        this.properties = properties;

        approvedIds = new HashMap<>();
        approvedIds.put("greg", "4611686018483187700");
        approvedIds.put("aatha", "4611686018483647447");
        approvedIds.put("korunde", "4611686018496457639");
        approvedIds.put("nbl", "4611686018496533524");
        approvedIds.put("vi24", "4611686018496642168");
        approvedIds.put("nigglz", "4611686018527421496");
        approvedIds.put("sani", "4611686018496487028");
        approvedIds.put("ren", "4611686018528099816");
        approvedIds.put("pebbles", "4611686018537799339");
        approvedIds.put("hoshi", "4611686018537819678");
        approvedIds.put("axolotl", "4611686018537959772");
    }

    private void schedulePowerLevelTask(TextChannel textChannel) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Task started at " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy - HH:mm:ss")));
                getPowerlevelUpdate(textChannel);
                System.out.println("Task finished at " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy - HH:mm:ss")));
            }
        };
        Timer timer = new Timer("Timer");
        long delay = 5000L;
        timer.schedule(task, delay, 3600000L);
    }

    public void getPowerlevelUpdate(TextChannel textChannel) {
        try {
            for (Map.Entry<String, String> approvedId : approvedIds.entrySet()) {
                ResponseData responseData = connectionBuilder.getPowerlevel(approvedId.getValue());
                if(responseData != null && responseData.getResponse() != null && responseData.getResponse().getCharacters() != null) {
                    for (Map.Entry<String, CharacterResponse> characterResponseEntry : responseData.getResponse().getCharacters().getData().entrySet()) {
                        CharacterResponse characterResponse = characterResponseEntry.getValue();
                        if (connectionBuilder.isLightHigher(approvedId.getKey(), characterResponse.getLight(), characterResponse.getClassType(), characterResponse.getCharacterId())) {
                            System.out.println("DEBUG: ApproveID: " + approvedId.getKey() + " / " + approvedId.getValue() + "\nLight: " + characterResponse.getLight() + "\nClasstype: " + characterResponse.getClassType() + "\nCharacterID: " + characterResponse.getCharacterId());
                            switch (characterResponse.getClassType()) {
                                case 0:
                                    textChannel.sendMessage("**" + approvedId.getKey() + "**'s Titan has a new power level: " + characterResponse.getLight()).queue();
                                    break;
                                case 1:
                                    textChannel.sendMessage("**" + approvedId.getKey() + "**'s Hunter has a new power level: " + characterResponse.getLight()).queue();
                                    break;
                                case 2:
                                    textChannel.sendMessage("**" + approvedId.getKey() + "**'s Warlock has a new power level: " + characterResponse.getLight()).queue();
                                    break;
                                default:
                                    textChannel.sendMessage("**" + approvedId.getKey() + "**  has a new power level: " + characterResponse.getLight()).queue();
                                    break;
                            }
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
        }
    }

    @Override
    public void onReady(ReadyEvent event){
        JDA jda = event.getJDA();
        Guild guild = jda.getGuildById(properties.getProperty("discord.dedicatedServer"));
        schedulePowerLevelTask(guild.getTextChannelById(properties.getProperty("discord.dedicatedTextChannel")));
    }
}
