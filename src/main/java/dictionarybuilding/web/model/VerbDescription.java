package dictionarybuilding.web.model;

import dictionarybuilding.web.Response;

import java.util.List;

public class VerbDescription implements Response {
    private String verb;
    private String transitivity;
    private String perfection;
    private int verbPosition;
    private String dictionaryRecord;
    private String sentence;
    private List<Word> sentenceWords;
    private List<Actant> actants;

    public String getVerb() {
        return verb;
    }

    public void setVerb(String verb) {
        this.verb = verb;
    }

    public String getTransitivity() {
        return transitivity;
    }

    public void setTransitivity(String transitivity) {
        this.transitivity = transitivity;
    }

    public String getPerfection() {
        return perfection;
    }

    public void setPerfection(String perfection) {
        this.perfection = perfection;
    }

    public int getVerbPosition() {
        return verbPosition;
    }

    public void setVerbPosition(int verbPosition) {
        this.verbPosition = verbPosition;
    }

    public String getDictionaryRecord() {
        return dictionaryRecord;
    }

    public void setDictionaryRecord(String dictionaryRecord) {
        this.dictionaryRecord = dictionaryRecord;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public List<Word> getSentenceWords() {
        return sentenceWords;
    }

    public void setSentenceWords(List<Word> sentenceWords) {
        this.sentenceWords = sentenceWords;
    }

    public List<Actant> getActants() {
        return actants;
    }

    public void setActants(List<Actant> actants) {
        this.actants = actants;
    }
}
