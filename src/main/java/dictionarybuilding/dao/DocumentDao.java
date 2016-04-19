package dictionarybuilding.dao;


import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Transactional
public class DocumentDao {

    @PersistenceContext
    private EntityManager entityManager;

    public String getText() {
        return (String) entityManager.createQuery(
                "select d.content from Document d"
        ).getSingleResult();
    }

}
