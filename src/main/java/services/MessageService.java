package services;

import models.profile.CharacterResponse;
import net.dv8tion.jda.api.EmbedBuilder;

public class MessageService {

    public EmbedBuilder sendPowerlevelUpdate(CharacterResponse characterResponse, String user) {
        EmbedBuilder embedBuilder = new EmbedBuilder();

        embedBuilder.setTitle("Power level update for **" + user + "**");
        switch (characterResponse.getClassType()) {
            case 0:
                embedBuilder.setDescription("Their Titan has a new power level: " + characterResponse.getLight());
                break;
            case 1:
                embedBuilder.setDescription("Their Hunter has a new power level: " + characterResponse.getLight());
                break;
            case 2:
                embedBuilder.setDescription("Their Warlock has a new power level: " + characterResponse.getLight());
                break;
            default:
                embedBuilder.setDescription("Their character has a new power level: " + characterResponse.getLight());
                break;
        }
        embedBuilder.setImage("https://www.bungie.net" + characterResponse.getEmblemPath());

        return embedBuilder;
    }
}
