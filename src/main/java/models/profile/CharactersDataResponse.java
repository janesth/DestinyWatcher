package models.profile;

import java.util.HashMap;

public class CharactersDataResponse {

    private HashMap<String, CharacterResponse> data;

    public HashMap<String, CharacterResponse> getData() {
        return data;
    }

    public void setData(HashMap<String, CharacterResponse> data) {
        this.data = data;
    }
}
