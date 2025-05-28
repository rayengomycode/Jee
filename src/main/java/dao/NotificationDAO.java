package dao;

import model.Notification;
import model.User;
import model.Voyage;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.time.LocalDateTime;
import java.util.List;

public class NotificationDAO extends GenericDAO<Notification> {

    public NotificationDAO() {
        super(Notification.class);
    }

    /**
     * Trouve toutes les notifications d'un utilisateur
     */
    public List<Notification> findByUser(User user) {
        Session session = sessionFactory.openSession();
        String hql = "FROM Notification n WHERE n.user.id = :userId ORDER BY n.dateCreation DESC";
        Query<Notification> query = session.createQuery(hql, Notification.class);
        query.setParameter("userId", user.getId());
        List<Notification> notifications = query.getResultList();
        session.close();
        return notifications;
    }

    /**
     * Trouve les notifications non lues d'un utilisateur
     */
    public List<Notification> findUnreadByUser(User user) {
        Session session = sessionFactory.openSession();
        String hql = "FROM Notification n WHERE n.user.id = :userId AND n.statut = 'NON_LUE' ORDER BY n.dateCreation DESC";
        Query<Notification> query = session.createQuery(hql, Notification.class);
        query.setParameter("userId", user.getId());
        List<Notification> notifications = query.getResultList();
        session.close();
        return notifications;
    }

    /**
     * Compte les notifications non lues d'un utilisateur
     */
    public long countUnreadByUser(User user) {
        Session session = sessionFactory.openSession();
        String hql = "SELECT COUNT(n) FROM Notification n WHERE n.user.id = :userId AND n.statut = 'NON_LUE'";
        Query<Long> query = session.createQuery(hql, Long.class);
        query.setParameter("userId", user.getId());
        Long count = query.uniqueResult();
        session.close();
        return count != null ? count : 0;
    }

    /**
     * Trouve les notifications par voyage
     */
    public List<Notification> findByVoyage(Voyage voyage) {
        Session session = sessionFactory.openSession();
        String hql = "FROM Notification n WHERE n.voyage.id = :voyageId ORDER BY n.dateCreation DESC";
        Query<Notification> query = session.createQuery(hql, Notification.class);
        query.setParameter("voyageId", voyage.getId());
        List<Notification> notifications = query.getResultList();
        session.close();
        return notifications;
    }

    /**
     * Trouve les notifications par type
     */
    public List<Notification> findByType(Notification.TypeNotification type) {
        Session session = sessionFactory.openSession();
        String hql = "FROM Notification n WHERE n.type = :type ORDER BY n.dateCreation DESC";
        Query<Notification> query = session.createQuery(hql, Notification.class);
        query.setParameter("type", type);
        List<Notification> notifications = query.getResultList();
        session.close();
        return notifications;
    }

    /**
     * Trouve les notifications à envoyer par email
     */
    public List<Notification> findPendingEmailNotifications() {
        Session session = sessionFactory.openSession();
        String hql = "FROM Notification n WHERE n.emailEnvoye = false AND n.user.email IS NOT NULL ORDER BY n.dateCreation ASC";
        Query<Notification> query = session.createQuery(hql, Notification.class);
        List<Notification> notifications = query.getResultList();
        session.close();
        return notifications;
    }

    /**
     * Trouve les notifications à envoyer par SMS
     */
    public List<Notification> findPendingSmsNotifications() {
        Session session = sessionFactory.openSession();
        String hql = "FROM Notification n WHERE n.smsEnvoye = false AND n.user.telephone IS NOT NULL " +
                     "AND n.type IN ('RETARD_VOYAGE', 'ANNULATION_VOYAGE', 'CHANGEMENT_QUAI') ORDER BY n.dateCreation ASC";
        Query<Notification> query = session.createQuery(hql, Notification.class);
        List<Notification> notifications = query.getResultList();
        session.close();
        return notifications;
    }

    /**
     * Marque une notification comme lue
     */
    public boolean markAsRead(Long notificationId) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            Notification notification = session.get(Notification.class, notificationId);
            if (notification != null) {
                notification.marquerCommeLue();
                session.merge(notification);
                session.getTransaction().commit();
                return true;
            }
            session.getTransaction().rollback();
            return false;
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    /**
     * Marque toutes les notifications d'un utilisateur comme lues
     */
    public boolean markAllAsReadForUser(User user) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            String hql = "UPDATE Notification SET statut = 'LUE', dateLecture = :now WHERE user.id = :userId AND statut = 'NON_LUE'";
            Query query = session.createQuery(hql);
            query.setParameter("now", LocalDateTime.now());
            query.setParameter("userId", user.getId());
            int updated = query.executeUpdate();
            session.getTransaction().commit();
            return updated > 0;
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    /**
     * Supprime les anciennes notifications (plus de 30 jours)
     */
    public int deleteOldNotifications() {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            LocalDateTime cutoffDate = LocalDateTime.now().minusDays(30);
            String hql = "DELETE FROM Notification WHERE dateCreation < :cutoffDate AND statut = 'ARCHIVEE'";
            Query query = session.createQuery(hql);
            query.setParameter("cutoffDate", cutoffDate);
            int deleted = query.executeUpdate();
            session.getTransaction().commit();
            return deleted;
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
            return 0;
        } finally {
            session.close();
        }
    }

    /**
     * Trouve les notifications récentes (dernières 24h)
     */
    public List<Notification> findRecentNotifications() {
        Session session = sessionFactory.openSession();
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
        String hql = "FROM Notification n WHERE n.dateCreation >= :yesterday ORDER BY n.dateCreation DESC";
        Query<Notification> query = session.createQuery(hql, Notification.class);
        query.setParameter("yesterday", yesterday);
        List<Notification> notifications = query.getResultList();
        session.close();
        return notifications;
    }
}
