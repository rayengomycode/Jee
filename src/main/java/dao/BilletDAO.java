package dao;

import model.Billet;
import model.User;
import model.Voyage;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class BilletDAO extends GenericDAO<Billet> {

    public BilletDAO() {
        super(Billet.class);
    }

    public List<Billet> findByUser(User user) {
        Session session = sessionFactory.openSession();
        Query<Billet> query = session.createQuery("from Billet where user.id = :userId", Billet.class);
        query.setParameter("userId", user.getId());
        List<Billet> billets = query.getResultList();
        session.close();
        return billets;
    }

    public List<Billet> findByUserAndEtat(User user, Billet.EtatBillet etat) {
        Session session = sessionFactory.openSession();
        Query<Billet> query = session.createQuery(
            "from Billet where user.id = :userId and etat = :etat", Billet.class);
        query.setParameter("userId", user.getId());
        query.setParameter("etat", etat);
        List<Billet> billets = query.getResultList();
        session.close();
        return billets;
    }

    public List<Billet> findByVoyage(Voyage voyage) {
        Session session = sessionFactory.openSession();
        Query<Billet> query = session.createQuery("from Billet where voyage.id = :voyageId", Billet.class);
        query.setParameter("voyageId", voyage.getId());
        List<Billet> billets = query.getResultList();
        session.close();
        return billets;
    }

    public boolean updateEtat(Long billetId, Billet.EtatBillet nouvelEtat) {
        Session session = sessionFactory.openSession();
        Billet billet = session.get(Billet.class, billetId);

        if (billet == null) {
            session.close();
            return false;
        }

        billet.setEtat(nouvelEtat);
        boolean success = update(billet);
        session.close();
        return success;
    }

    @Override
    public List<Billet> findAll() {
        Session session = sessionFactory.openSession();
        String hql = "SELECT DISTINCT b FROM Billet b " +
                     "LEFT JOIN FETCH b.user " +
                     "LEFT JOIN FETCH b.voyage v " +
                     "LEFT JOIN FETCH v.trajet t " +
                     "LEFT JOIN FETCH t.gareTrajets gt " +
                     "LEFT JOIN FETCH gt.gare " +
                     "LEFT JOIN FETCH v.train " +
                     "LEFT JOIN FETCH b.classe " +
                     "ORDER BY b.dateAchat DESC";

        Query<Billet> query = session.createQuery(hql, Billet.class);
        List<Billet> billets = query.getResultList();
        session.close();
        return billets;
    }

    public Billet findByIdWithRelations(Long id) {
        Session session = sessionFactory.openSession();
        String hql = "SELECT b FROM Billet b " +
                     "LEFT JOIN FETCH b.user " +
                     "LEFT JOIN FETCH b.voyage v " +
                     "LEFT JOIN FETCH v.trajet t " +
                     "LEFT JOIN FETCH t.gareTrajets gt " +
                     "LEFT JOIN FETCH gt.gare " +
                     "LEFT JOIN FETCH v.train " +
                     "LEFT JOIN FETCH b.classe " +
                     "WHERE b.id = :id";

        Query<Billet> query = session.createQuery(hql, Billet.class);
        query.setParameter("id", id);
        Billet billet = query.uniqueResult();
        session.close();
        return billet;
    }
}
