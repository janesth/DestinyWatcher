package models.profile;

public class CharacterResponse {

    private int baseCharacterLevel;
    private long characterId;
    private long classHash;
    private int classType;
    private int light;

    public int getBaseCharacterLevel() {
        return baseCharacterLevel;
    }

    public void setBaseCharacterLevel(int baseCharacterLevel) {
        this.baseCharacterLevel = baseCharacterLevel;
    }

    public long getCharacterId() {
        return characterId;
    }

    public void setCharacterId(long characterId) {
        this.characterId = characterId;
    }

    public long getClassHash() {
        return classHash;
    }

    public void setClassHash(long classHash) {
        this.classHash = classHash;
    }

    public int getClassType() {
        return classType;
    }

    public void setClassType(int classType) {
        this.classType = classType;
    }

    public int getLight() {
        return light;
    }

    public void setLight(int light) {
        this.light = light;
    }
}
