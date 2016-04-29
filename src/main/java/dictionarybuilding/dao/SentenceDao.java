package dictionarybuilding.dao;


import dictionarybuilding.model.Sentence;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Transactional
public class SentenceDao {

    @PersistenceContext
    private EntityManager entityManager;

    public Sentence addSentence(Sentence sentence) {
        entityManager.persist(sentence);
        entityManager.flush();
        return sentence;
    }

}
