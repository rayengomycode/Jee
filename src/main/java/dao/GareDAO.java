package dao;

import model.Gare;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class GareDAO extends GenericDAO<Gare> {
    
    public GareDAO() {
        super(Gare.class);
    }
    
    public Gare findByNomAndVille(String nom, String ville) {
        Session session = sessionFactory.openSession();
        Query<Gare> query = session.createQuery(
            "from Gare where nom = :nom and ville = :ville", Gare.class);
        query.setParameter("nom", nom);
        query.setParameter("ville", ville);
        Gare gare = query.uniqueResult();
        session.close();
        return gare;
    }
    
    public List<Gare> findByVille(String ville) {
        Session session = sessionFactory.openSession();
        Query<Gare> query = session.createQuery("from Gare where ville = :ville", Gare.class);
        query.setParameter("ville", ville);
        List<Gare> gares = query.getResultList();
        session.close();
        return gares;
    }
    
    public List<String> findAllVilles() {
        Session session = sessionFactory.openSession();
        Query<String> query = session.createQuery(
            "select distinct ville from Gare order by ville", String.class);
        List<String> villes = query.getResultList();
        session.close();
        return villes;
    }
}
