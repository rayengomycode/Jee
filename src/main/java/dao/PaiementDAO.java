package dao;

import model.Paiement;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class PaiementDAO extends GenericDAO<Paiement> {
    
    public PaiementDAO() {
        super(Paiement.class);
    }
    
    public Paiement findByReference(String reference) {
        Session session = sessionFactory.openSession();
        Query<Paiement> query = session.createQuery("from Paiement where reference = :reference", Paiement.class);
        query.setParameter("reference", reference);
        Paiement paiement = query.uniqueResult();
        session.close();
        return paiement;
    }
}
