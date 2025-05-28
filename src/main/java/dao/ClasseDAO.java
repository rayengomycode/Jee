package dao;

import model.Classe;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class ClasseDAO extends GenericDAO<Classe> {
    
    public ClasseDAO() {
        super(Classe.class);
    }
    
    public Classe findByNom(String nom) {
        Session session = sessionFactory.openSession();
        Query<Classe> query = session.createQuery("from Classe where nom = :nom", Classe.class);
        query.setParameter("nom", nom);
        Classe classe = query.uniqueResult();
        session.close();
        return classe;
    }
}
