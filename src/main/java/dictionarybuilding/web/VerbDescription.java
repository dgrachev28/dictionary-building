package dictionarybuilding.web;

public class VerbDescription implements Response {
    private String verb;
    private String dictionaryRecord;
    private String sentence;

    public String getVerb() {
        return verb;
    }

    public void setVerb(String verb) {
        this.verb = verb;
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
}
