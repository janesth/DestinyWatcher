package models.profile;

public class ResponseProfile {

    private CharacterEquipmentDataResponse characterEquipment;
    private CharactersDataResponse characters;
    private ProfileDataResponse profile;
    private int privacy;

    public CharacterEquipmentDataResponse getCharacterEquipment() {
        return characterEquipment;
    }

    public void setCharacterEquipment(CharacterEquipmentDataResponse characterEquipment) {
        this.characterEquipment = characterEquipment;
    }

    public CharactersDataResponse getCharacters() {
        return characters;
    }

    public void setCharacters(CharactersDataResponse characters) {
        this.characters = characters;
    }

    public ProfileDataResponse getProfile() {
        return profile;
    }

    public void setProfile(ProfileDataResponse profile) {
        this.profile = profile;
    }

    public int getPrivacy() {
        return privacy;
    }

    public void setPrivacy(int privacy) {
        this.privacy = privacy;
    }
}
