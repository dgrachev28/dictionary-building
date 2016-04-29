package dictionarybuilding.service;

import dictionarybuilding.dao.SentenceDao;
import dictionarybuilding.dao.VerbUseDao;
import dictionarybuilding.model.Sentence;
import dictionarybuilding.model.VerbUse;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.namespace.QName;
import java.io.File;

@Service
public class VerbEntranceService {

    @Autowired
    private SentenceDao sentenceDao;

    @Autowired
    private VerbUseDao verbUseDao;

    public VerbUse getNextVerbEntrance(String verb) {
        VerbUse verbUse = verbUseDao.getVerb(verb);
//        return verbUse.getSentence().getText();
        return verbUse;
    }
}
