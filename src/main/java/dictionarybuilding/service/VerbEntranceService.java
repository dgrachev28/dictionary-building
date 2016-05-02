package dictionarybuilding.service;

import dictionarybuilding.dao.VerbUseDao;
import dictionarybuilding.model.VerbUse;
import dictionarybuilding.web.AppException;
import dictionarybuilding.web.model.Actant;
import dictionarybuilding.web.model.VerbDescription;
import dictionarybuilding.web.model.Word;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.type.filter.AbstractClassTestingTypeFilter;
import org.springframework.stereotype.Service;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.jar.Pack200;

@Service
public class VerbEntranceService {

    @Autowired
    private VerbUseDao verbUseDao;

    @Autowired
    private SentenceParser sentenceParser;

    public VerbDescription getNextVerbEntrance(String verb) {
        VerbUse verbUse = verbUseDao.getVerb(verb);

        VerbDescription verbDescription = new VerbDescription();
        verbDescription.setSentence(verbUse.getSentence().getText());
        verbDescription.setSentenceWords(sentenceParser.parse(verbUse.getSentence()));

        setWordsPartOfSpeech(verbDescription.getSentenceWords());
        setVerbProperties(verbDescription, verbUse);
        verbDescription.setActants(findActants(verbDescription.getSentenceWords(), verbUse));

        System.out.println(verbUse.getSentence().getText());

        return verbDescription;
    }

    private void setVerbProperties(VerbDescription verbDescription, VerbUse verbUse) {
        int position = verbUse.getPosition().intValue();
        verbDescription.setVerbPosition(position);

        Word verbWord = verbDescription.getSentenceWords().get(position);
        List<String> verbGramInfo = verbWord.getWordVariants().get(0).getGramInfo();

        if (verbGramInfo.contains("tran")) {
            verbDescription.setTransitivity("п");
        } else if (verbGramInfo.contains("intr")) {
            verbDescription.setTransitivity("нп");
        } else {
            throw new AppException("Глагол " + verbUse.getVerb() + " не содержит информации о переходности: " + verbGramInfo);
        }

        if (verbGramInfo.contains("pf")) {
            verbDescription.setPerfection("св");
        } else if (verbGramInfo.contains("ipf")) {
            verbDescription.setPerfection("нсв");
        } else {
            throw new AppException("Глагол " + verbUse.getVerb() + " не содержит информации о виде: " + verbGramInfo);
        }
        verbDescription.setVerb(verbUse.getVerb());
    }

    private void setWordsPartOfSpeech(List<Word> words) {
        for (Word word : words) {
            if (word.getWordVariants().size() > 0) {
                word.setPartOfSpeech(word.getWordVariants().get(0).getPartOfSpeech());
            } else {
                word.setPartOfSpeech("");
            }
        }
    }

    private List<Actant> findActants(List<Word> words, VerbUse verbUse) {
        List<Actant> actants = new ArrayList<Actant>();
        int startPosition = verbUse.getPosition().intValue() + 1;
        while (true) {
            Actant actant = findActantFromPosition(words, startPosition);
            if (actant == null) {
                break;
            }
            startPosition = actant.getNounPosition();
            actants.add(actant);
        }
        return actants;
    }

    private Actant findActantFromPosition(List<Word> words, int startPosition) {
        int previousWordPosition = startPosition;
        for (int i = startPosition + 1; i < words.size(); i++) {
            Word word = words.get(i);
            if (word.getPartOfSpeech().equals("APRO") || word.getPartOfSpeech().equals("A")) {
                continue;
            }
            if (words.get(previousWordPosition).getPartOfSpeech().equals("PR")
                    && word.getPartOfSpeech().equals("S")) {
                Actant actant = new Actant();
                actant.setPretextPosition(previousWordPosition);
                actant.setNounPosition(i);
                actant.setActantType("DO");
                setNounProperties(word, actant);

                return actant;
            }
            previousWordPosition = i;
        }
        return null;
    }

    private void setNounProperties(Word word, Actant actant) {
        List<String> gramInfo = word.getWordVariants().get(0).getGramInfo();
        int animIndex = gramInfo.indexOf("anim");
        int inanIndex = gramInfo.indexOf("inan");
        int index = Math.max(animIndex, inanIndex);
        if (animIndex != -1) {
            actant.setAnimacy("о");
        } else if (inanIndex != -1) {
            actant.setAnimacy("но");
        } else {
            throw new AppException("Для существительного + \"" + word.getWord() + "\" нет свойства одушевленности: " + gramInfo);
        }

        if (index + 1 < gramInfo.size()) {
            actant.setNounCase(gramInfo.get(index + 1));
        } else {
            throw new AppException("После свойства одушевленности нет свойства падежа: " + gramInfo);
        }
    }

    private void getNounsAfterVerb(VerbUse verbUse) throws Exception {
        List<String> wordsPartOfSpeech = new ArrayList<String>();
        List<String> wordsLexeme = new ArrayList<String>();

        XmlObject xmlObject = XmlObject.Factory.parse(verbUse.getSentence().getXml());
        XmlCursor wordCursor = xmlObject.newCursor();
        wordCursor.selectPath(".//w");

        for (int i = 0; i < verbUse.getPosition() + 1; i++) {
            wordCursor.toNextSelection();
        }

        while (wordCursor.toNextSelection()) {
            String partOfSpeech = getPartOfSpeech(wordCursor.newCursor(), 0.5);
            wordsPartOfSpeech.add(partOfSpeech);
            wordsLexeme.add(wordCursor.getTextValue().trim());
        }

        if (wordsPartOfSpeech.size() >= 2) {
            if (wordsPartOfSpeech.get(0).equals("PR") && wordsPartOfSpeech.get(1).equals("S")) {
                System.out.println(wordsLexeme.get(0) + " " + wordsLexeme.get(1));
            }
        }


    }

    private String getPartOfSpeech(XmlCursor anaCursor, double minWeight) {
        anaCursor.selectPath(".//ana");
        while (anaCursor.toNextSelection()) {
//           String lexeme = anaCursor.getAttributeText(new QName("lex"));
            String gramInfo = anaCursor.getAttributeText(new QName("gr"));
            Double weight = Double.parseDouble(anaCursor.getAttributeText(new QName("wt")));
            if (weight > minWeight) {
                int commaPosition = gramInfo.indexOf(',');
                if (commaPosition == -1) {
                    return gramInfo.substring(0, gramInfo.indexOf('='));
                } else {
                    return gramInfo.substring(0, commaPosition);
                }
            }
        }
        return "";
    }
}
