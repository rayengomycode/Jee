package dao;

import model.Role;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class RoleDAO extends GenericDAO<Role> {
    
    public RoleDAO() {
        super(Role.class);
    }
    
    public Role findByNom(String nom) {
        Session session = sessionFactory.openSession();
        Query<Role> query = session.createQuery("from Role where nom = :nom", Role.class);
        query.setParameter("nom", nom);
        Role role = query.uniqueResult();
        session.close();
        return role;
    }
}
