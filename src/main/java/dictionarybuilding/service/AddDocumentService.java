package dictionarybuilding.service;

import dictionarybuilding.dao.SentenceDao;
import dictionarybuilding.dao.VerbUseDao;
import dictionarybuilding.model.Sentence;
import dictionarybuilding.model.VerbUse;
import dictionarybuilding.web.AppException;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.namespace.QName;
import java.io.*;

@Service
public class AddDocumentService {

    @Autowired
    private SentenceDao sentenceDao;

    @Autowired
    private VerbUseDao verbUseDao;

    public void clearVerbs() {
        verbUseDao.setAllVerbsUnused();
    }

    public void addDocument() {
        addDocument("mystem_out.xml");
    }

    private void addDocument(String xmlFile) {
        try {
            XmlObject xobj = XmlObject.Factory.parse(new File(RealPathService.resourcesFolder + xmlFile));
            XmlCursor sentenceCursor = xobj.newCursor();
            sentenceCursor.selectPath("*//se");
            int sentenceCounter = 0;
            while (sentenceCursor.toNextSelection()) {
                String sentenceText = sentenceCursor.getTextValue().replaceAll("\n", "");
                String sentenceXml = sentenceCursor.getObject().toString();

                Sentence sentence = new Sentence(sentenceText, sentenceXml);
                if (sentenceCounter < 5) {
                    sentence = sentenceDao.addUniqueSentence(sentence);
                    ++sentenceCounter;
                } else {
                    sentence = sentenceDao.addSentence(sentence);
                }

                addSentenceVerbs(sentence);

            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException(e.getMessage());
        }
    }

    private void addSentenceVerbs(Sentence sentence) throws Exception {
        XmlObject xmlObject = XmlObject.Factory.parse(sentence.getXml());
        XmlCursor wordCursor = xmlObject.newCursor();
        wordCursor.selectPath(".//w");

        Long position = 0L;
        while (wordCursor.toNextSelection()) {
            XmlCursor anaCursor = wordCursor.newCursor();
            anaCursor.selectPath(".//ana");
            Boolean isVerb = false;
            String lexeme = "";
            while (anaCursor.toNextSelection()) {
                isVerb = true;
                lexeme = anaCursor.getAttributeText(new QName("lex"));
                String gramInfo = anaCursor.getAttributeText(new QName("gr"));

                if (gramInfo.indexOf("V") != 0 || !gramInfo.contains("indic")) {
                    isVerb = false;
                    break;
                }
            }

            if (!isVerb) {
                position++;
                continue;
            }

            VerbUse verbUse = new VerbUse(lexeme, sentence, false, position);
            verbUseDao.addVerbUse(verbUse);
            position++;
        }

    }

}
