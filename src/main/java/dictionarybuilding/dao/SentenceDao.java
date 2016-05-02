package dictionarybuilding.dao;


import dictionarybuilding.model.Sentence;
import dictionarybuilding.web.AppException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.xml.namespace.QName;

@Repository
@Transactional
public class SentenceDao {

    @PersistenceContext
    private EntityManager entityManager;

    public Sentence addUniqueSentence(Sentence sentence) {
        Query query = entityManager.createQuery("select s from Sentence s where s.text = :text");
        query.setParameter("text", sentence.getText());
        if (query.getResultList().size() > 0) {
            throw new AppException("Текст содержит предложение, которое есть в базе. Скорее всего такой текст уже загружен в базу. Загрузите другой текст");
        }
        return addSentence(sentence);
    }

    public Sentence addSentence(Sentence sentence) {
        entityManager.persist(sentence);
        entityManager.flush();
        return sentence;
    }

}
