import listeners.DestinyBotListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class StartUp {

    private static final DateTimeFormatter START_TIME = DateTimeFormatter.ofPattern("dd.MM.yyyy - HH:mm:ss");

    public static void main(String[] args) {
        try {

            String startMessage = LocalDateTime.now().format(START_TIME) + " - Started application 'Destiny Watcher'.";
            System.out.println(startMessage);

            InputStream inputStream = StartUp.class.getClassLoader().getResourceAsStream("config.properties");
            Properties properties = new Properties();
            properties.load(inputStream);

            JDA jda = JDABuilder.createDefault(properties.getProperty("discord.apiToken"))
                    .addEventListeners(new DestinyBotListener(properties))
                    .build();

            jda.getPresence().setActivity(Activity.playing("YOINC.ch"));
            jda.awaitReady();
        } catch (SQLException ex) {
            System.out.println("Nice. Problems with the database.");
        } catch (InterruptedException ex) {
            System.out.println("Nice. Something interrupted the connection.");
        } catch (IOException ex) {
            System.out.println("Nice. Problems with that property.");
        }
    }
}
