package dictionarybuilding.web.model;


public class Actant {
    private int nounPosition;
    private int pretextPosition;
    private String nounCase;
    private String animacy;
    private String actantType;
    private String question;

    public int getNounPosition() {
        return nounPosition;
    }

    public void setNounPosition(int nounPosition) {
        this.nounPosition = nounPosition;
    }

    public int getPretextPosition() {
        return pretextPosition;
    }

    public void setPretextPosition(int pretextPosition) {
        this.pretextPosition = pretextPosition;
    }

    public String getNounCase() {
        return nounCase;
    }

    public void setNounCase(String nounCase) {
        this.nounCase = nounCase;
    }

    public String getAnimacy() {
        return animacy;
    }

    public void setAnimacy(String animacy) {
        this.animacy = animacy;
    }

    public String getActantType() {
        return actantType;
    }

    public void setActantType(String actantType) {
        this.actantType = actantType;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
