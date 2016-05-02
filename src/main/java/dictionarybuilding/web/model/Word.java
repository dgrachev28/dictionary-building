package dictionarybuilding.web.model;

import java.util.List;

public class Word {
    private String word;
    private List<WordVariant> wordVariants;
    private String delimiter;
    private String partOfSpeech;

    public Word(String word, List<WordVariant> wordVariants, String delimiter) {
        this.word = word;
        this.wordVariants = wordVariants;
        this.delimiter = delimiter;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public List<WordVariant> getWordVariants() {
        return wordVariants;
    }

    public void setWordVariants(List<WordVariant> wordVariants) {
        this.wordVariants = wordVariants;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

}
