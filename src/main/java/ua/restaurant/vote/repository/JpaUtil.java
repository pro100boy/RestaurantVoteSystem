package ua.restaurant.vote.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.persister.collection.AbstractCollectionPersister;
import org.hibernate.persister.entity.EntityPersister;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Map;

/**
 * Galushkin Pavel
 * 04.03.2017
 */
public class JpaUtil {

    @PersistenceContext
    private EntityManager em;

    public void clear2ndLevelHibernateCache() {
        Session s = (Session) em.getDelegate();
        SessionFactory sf = s.getSessionFactory();
        sf.getCache().evictAllRegions();
    }
}
