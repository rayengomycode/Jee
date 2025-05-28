package dao;

import model.Voyage;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.util.List;

public class VoyageDAO extends GenericDAO<Voyage> {

    public VoyageDAO() {
        super(Voyage.class);
    }

    public List<Voyage> findByDateAndVilles(LocalDate date, String villeDepart, String villeArrivee) {
        Session session = sessionFactory.openSession();

        String hql = "SELECT v FROM Voyage v " +
                     "JOIN v.trajet t " +
                     "JOIN t.gareTrajets gtDepart " +
                     "JOIN gtDepart.gare gDepart " +
                     "JOIN t.gareTrajets gtArrivee " +
                     "JOIN gtArrivee.gare gArrivee " +
                     "WHERE v.date = :date " +
                     "AND gDepart.ville = :villeDepart " +
                     "AND gArrivee.ville = :villeArrivee " +
                     "AND gtDepart.ordre < gtArrivee.ordre " +
                     "AND v.placesDisponibles > 0 " +
                     "ORDER BY v.heureDepart";

        Query<Voyage> query = session.createQuery(hql, Voyage.class);
        query.setParameter("date", date);
        query.setParameter("villeDepart", villeDepart);
        query.setParameter("villeArrivee", villeArrivee);

        List<Voyage> voyages = query.getResultList();
        session.close();
        return voyages;
    }

    public List<Voyage> findDirectByDateAndVilles(LocalDate date, String villeDepart, String villeArrivee) {
        Session session = sessionFactory.openSession();

        String hql = "SELECT v FROM Voyage v " +
                     "JOIN v.trajet t " +
                     "JOIN t.gareTrajets gtDepart " +
                     "JOIN gtDepart.gare gDepart " +
                     "JOIN t.gareTrajets gtArrivee " +
                     "JOIN gtArrivee.gare gArrivee " +
                     "WHERE v.date = :date " +
                     "AND gDepart.ville = :villeDepart " +
                     "AND gArrivee.ville = :villeArrivee " +
                     "AND gtDepart.ordre < gtArrivee.ordre " +
                     "AND t.direct = true " +
                     "AND v.placesDisponibles > 0 " +
                     "ORDER BY v.heureDepart";

        Query<Voyage> query = session.createQuery(hql, Voyage.class);
        query.setParameter("date", date);
        query.setParameter("villeDepart", villeDepart);
        query.setParameter("villeArrivee", villeArrivee);

        List<Voyage> voyages = query.getResultList();
        session.close();
        return voyages;
    }

    public boolean updatePlacesDisponibles(Long voyageId, int nombrePlaces) {
        Session session = sessionFactory.openSession();
        org.hibernate.Transaction tx = null;
        boolean success = false;

        try {
            tx = session.beginTransaction();
            Voyage voyage = session.get(Voyage.class, voyageId);

            if (voyage == null || voyage.getPlacesDisponibles() < nombrePlaces) {
                return false;
            }

            voyage.setPlacesDisponibles(voyage.getPlacesDisponibles() - nombrePlaces);
            session.merge(voyage);
            tx.commit();
            success = true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return success;
    }

    @Override
    public List<Voyage> findAll() {
        Session session = sessionFactory.openSession();
        String hql = "SELECT DISTINCT v FROM Voyage v " +
                     "LEFT JOIN FETCH v.trajet t " +
                     "LEFT JOIN FETCH t.gareTrajets gt " +
                     "LEFT JOIN FETCH gt.gare " +
                     "LEFT JOIN FETCH v.train " +
                     "ORDER BY v.date, v.heureDepart";

        Query<Voyage> query = session.createQuery(hql, Voyage.class);
        List<Voyage> voyages = query.getResultList();
        session.close();
        return voyages;
    }

    public Voyage findByIdWithRelations(Long id) {
        Session session = sessionFactory.openSession();
        String hql = "SELECT v FROM Voyage v " +
                     "LEFT JOIN FETCH v.trajet t " +
                     "LEFT JOIN FETCH t.gareTrajets gt " +
                     "LEFT JOIN FETCH gt.gare " +
                     "LEFT JOIN FETCH v.train " +
                     "WHERE v.id = :id";

        Query<Voyage> query = session.createQuery(hql, Voyage.class);
        query.setParameter("id", id);
        Voyage voyage = query.uniqueResult();
        session.close();
        return voyage;
    }
}
