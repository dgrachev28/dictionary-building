package dictionarybuilding.service;

import dictionarybuilding.dao.SentenceDao;
import dictionarybuilding.dao.VerbUseDao;
import dictionarybuilding.model.Sentence;
import dictionarybuilding.model.VerbUse;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.hibernate.annotations.common.reflection.XClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.namespace.QName;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class AddDocumentService {

    @Autowired
    private SentenceDao sentenceDao;

    @Autowired
    private VerbUseDao verbUseDao;

    public void test() {
        addDocument("mystem_out.xml");
    }

    private void addDocument(String xmlFile) {
        try {
            XmlObject xobj = XmlObject.Factory.parse(new File(RealPathService.resourcesFolder + xmlFile));
            XmlCursor sentenceCursor = xobj.newCursor();
            sentenceCursor.selectPath("*//se");
            while (sentenceCursor.toNextSelection()) {
                String sentenceText = sentenceCursor.getTextValue().replaceAll("\n", "");
                String sentenceXml = sentenceCursor.getObject().toString();

                Sentence sentence = new Sentence(sentenceText, sentenceXml);
                sentence = sentenceDao.addSentence(sentence);

                addSentenceVerbs(sentence);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addSentenceVerbs(Sentence sentence) throws Exception {
        XmlObject xmlObject = XmlObject.Factory.parse(sentence.getXml());
        XmlCursor wordCursor = xmlObject.newCursor();
        wordCursor.selectPath(".//w");

        while (wordCursor.toNextSelection()) {
            XmlCursor anaCursor = wordCursor.newCursor();
            anaCursor.selectPath(".//ana");
            Boolean isVerb = true;
            String lexeme = "";
            while (anaCursor.toNextSelection()) {
                lexeme = anaCursor.getAttributeText(new QName("lex"));
                String gramInfo = anaCursor.getAttributeText(new QName("gr"));

                if (gramInfo.indexOf("V") != 0 || !gramInfo.contains("indic")) {
                    isVerb = false;
                    break;
                }
            }

            if (!isVerb) {
                continue;
            }

            VerbUse verbUse = new VerbUse(lexeme, sentence, false);
            verbUseDao.addVerbUse(verbUse);
        }

    }

}
