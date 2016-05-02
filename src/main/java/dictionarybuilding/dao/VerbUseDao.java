package dictionarybuilding.dao;


import dictionarybuilding.model.VerbUse;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
@Transactional
public class VerbUseDao {

    @PersistenceContext
    private EntityManager entityManager;

    public void addVerbUse(VerbUse verbUse) {
        entityManager.persist(verbUse);
    }

    public VerbUse getVerb(String verb) {
        Query query = entityManager.createQuery(
                "select v from VerbUse v where v.verb = :verb and v.used = false"
        );
        query.setParameter("verb", verb);
        query.setFirstResult(0);
        query.setMaxResults(1);
        VerbUse verbUse = (VerbUse) query.getResultList().get(0);
        verbUse.setUsed(true);
        entityManager.persist(verbUse);
        return verbUse;
    }

    public void setAllVerbsUnused() {
        entityManager.createQuery("update VerbUse v set used = false").executeUpdate();
    }

}
