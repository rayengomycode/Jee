package dao;

import model.Role;
import model.User;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class UserDAO extends GenericDAO<User> {

    public UserDAO() {
        super(User.class);
    }

    public User findByUsername(String username) {
        Session session = sessionFactory.openSession();
        Query<User> query = session.createQuery("from User where username = :username", User.class);
        query.setParameter("username", username);
        User user = query.uniqueResult();
        session.close();
        return user;
    }

    public User findByEmail(String email) {
        Session session = sessionFactory.openSession();
        Query<User> query = session.createQuery("from User where email = :email", User.class);
        query.setParameter("email", email);
        User user = query.uniqueResult();
        session.close();
        return user;
    }

    public boolean updateStatut(Long userId, boolean actif) {
        Session session = sessionFactory.openSession();
        User user = session.get(User.class, userId);

        if (user == null) {
            session.close();
            return false;
        }

        user.setActif(actif);
        boolean success = update(user);
        session.close();
        return success;
    }

    public java.util.List<User> findByRole(Role role) {
        Session session = sessionFactory.openSession();
        Query<User> query = session.createQuery("from User where role.id = :roleId", User.class);
        query.setParameter("roleId", role.getId());
        java.util.List<User> users = query.getResultList();
        session.close();
        return users;
    }
}
