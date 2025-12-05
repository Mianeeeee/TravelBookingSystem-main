package Model;

import java.util.*;

public class Guide extends Human {
    private float GuideExperience;
    private ArrayList<String> ForeignLanguage;

    public Guide() {
        super();
        this.GuideExperience = 0.0F;
        this.ForeignLanguage = new ArrayList<>();
    }

    public float getGuideExperience() {
        return GuideExperience;
    }

    public void setGuideExperience(float GuideExperience) {
        this.GuideExperience = GuideExperience;
    }

    public ArrayList<String> getForeignLanguage() {
        return this.ForeignLanguage;
    }

    public void setForeignLanguage(ArrayList<String> ForeignLanguage) {
        this.ForeignLanguage = ForeignLanguage;
    }

    public String getForeignLanguageAsString() {
        StringBuilder Langs = new StringBuilder();
        for (String lang : this.ForeignLanguage) {
            Langs.append(lang).append(", ");
        }

        return Langs.substring(0, Langs.length() - 2);
    }
}
