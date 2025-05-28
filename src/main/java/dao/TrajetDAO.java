package dao;

import model.Gare;
import model.Trajet;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class TrajetDAO extends GenericDAO<Trajet> {

    public TrajetDAO() {
        super(Trajet.class);
    }

    public Trajet findByCode(String code) {
        Session session = sessionFactory.openSession();
        Query<Trajet> query = session.createQuery("from Trajet where code = :code", Trajet.class);
        query.setParameter("code", code);
        Trajet trajet = query.uniqueResult();
        session.close();
        return trajet;
    }

    public List<Trajet> findByGareDepart(Gare gareDepart) {
        Session session = sessionFactory.openSession();
        String hql = "SELECT DISTINCT t FROM Trajet t " +
                     "JOIN t.gareTrajets gt " +
                     "WHERE gt.gare.id = :gareId " +
                     "AND gt.ordre = 1";

        Query<Trajet> query = session.createQuery(hql, Trajet.class);
        query.setParameter("gareId", gareDepart.getId());
        List<Trajet> trajets = query.getResultList();
        session.close();
        return trajets;
    }

    public List<Trajet> findByGareDepartAndGareArrivee(Gare gareDepart, Gare gareArrivee) {
        Session session = sessionFactory.openSession();
        String hql = "SELECT DISTINCT t FROM Trajet t " +
                     "JOIN t.gareTrajets gtDepart " +
                     "JOIN t.gareTrajets gtArrivee " +
                     "WHERE gtDepart.gare.id = :gareDepart " +
                     "AND gtArrivee.gare.id = :gareArrivee " +
                     "AND gtDepart.ordre < gtArrivee.ordre";

        Query<Trajet> query = session.createQuery(hql, Trajet.class);
        query.setParameter("gareDepart", gareDepart.getId());
        query.setParameter("gareArrivee", gareArrivee.getId());
        List<Trajet> trajets = query.getResultList();
        session.close();
        return trajets;
    }

    @Override
    public List<Trajet> findAll() {
        Session session = sessionFactory.openSession();
        String hql = "SELECT DISTINCT t FROM Trajet t " +
                     "LEFT JOIN FETCH t.gareTrajets gt " +
                     "LEFT JOIN FETCH gt.gare " +
                     "ORDER BY t.id";

        Query<Trajet> query = session.createQuery(hql, Trajet.class);
        List<Trajet> trajets = query.getResultList();
        session.close();
        return trajets;
    }

    public Trajet findByIdWithGares(Long id) {
        Session session = sessionFactory.openSession();
        String hql = "SELECT t FROM Trajet t " +
                     "LEFT JOIN FETCH t.gareTrajets gt " +
                     "LEFT JOIN FETCH gt.gare " +
                     "WHERE t.id = :id";

        Query<Trajet> query = session.createQuery(hql, Trajet.class);
        query.setParameter("id", id);
        Trajet trajet = query.uniqueResult();
        session.close();
        return trajet;
    }
}
