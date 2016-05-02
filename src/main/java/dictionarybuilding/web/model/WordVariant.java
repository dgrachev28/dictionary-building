package dictionarybuilding.web.model;

import dictionarybuilding.web.AppException;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordVariant implements Comparable<WordVariant> {
    private String lexeme;
    private Double weight;
    private List<String> gramInfo;
    private String partOfSpeech;


    public WordVariant(String lexeme, Double weight, String gramInfo) {
        this.lexeme = lexeme;
        this.weight = weight;
        setGramInfo(gramInfo);
    }

    public WordVariant(String lexeme, Double weight, String gramInfo, String partOfSpeech) {
        this(lexeme, weight, gramInfo);
        this.partOfSpeech = partOfSpeech;
    }

    public String getLexeme() {
        return lexeme;
    }

    public void setLexeme(String lexeme) {
        this.lexeme = lexeme;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public List<String> getGramInfo() {
        return gramInfo;
    }

    public void setGramInfo(String gramInfo) {
        this.gramInfo = Arrays.asList(gramInfo.split("[,=]"));
        setPartOfSpeech(gramInfo);
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    private void setPartOfSpeech(String gramInfo) {
        Pattern p = Pattern.compile("([A-Z]+)");
        Matcher m = p.matcher(gramInfo);
        if (m.find()) {
            partOfSpeech = m.group(1);
        } else {
            throw new AppException("Ошибка при выделении части речи из gramInfo: " + gramInfo + ", лексема: " + lexeme);
        }
    }

    @Override
    public int compareTo(WordVariant wordVariant) {
        if (this.weight > wordVariant.weight) {
            return -1;
        } else {
            return 1;
        }
    }
}
